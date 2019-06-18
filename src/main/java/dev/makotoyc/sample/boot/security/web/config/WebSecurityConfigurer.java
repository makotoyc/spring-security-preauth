package dev.makotoyc.sample.boot.security.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AccountStatusUserDetailsChecker;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationProvider;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import dev.makotoyc.sample.boot.security.core.userdetails.PreAuthenticatedUserDetailsService;
import dev.makotoyc.sample.boot.security.web.authentication.PreAuthenticatedProcessingFilter;

@EnableWebSecurity
public class WebSecurityConfigurer extends WebSecurityConfigurerAdapter {

  @Bean
  public AuthenticationUserDetailsService<PreAuthenticatedAuthenticationToken> authenticationUserDetailsService() {
    return new PreAuthenticatedUserDetailsService();
  }

  @Bean
  public PreAuthenticatedAuthenticationProvider preAuthenticatedAuthenticationProvider() {
    PreAuthenticatedAuthenticationProvider provider =
        new PreAuthenticatedAuthenticationProvider();
    provider.setPreAuthenticatedUserDetailsService(authenticationUserDetailsService());
    provider.setUserDetailsChecker(new AccountStatusUserDetailsChecker());
    return provider;
  }

  @Bean
  public AbstractPreAuthenticatedProcessingFilter preAuthenticatedProcessingFilter()
      throws Exception {
    PreAuthenticatedProcessingFilter filter = new PreAuthenticatedProcessingFilter();
    filter.setAuthenticationManager(authenticationManager());
    return filter;
  }

  @Bean
  public JsonAccessDeniedHandler accessDeniedHandler() {
    return new JsonAccessDeniedHandler();
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    SecurityContextHolder.clearContext();
    http.addFilter(preAuthenticatedProcessingFilter())
        .exceptionHandling().accessDeniedHandler(accessDeniedHandler())
        .and()
        .authorizeRequests().mvcMatchers("/admin/api/v1/**").hasAnyRole("EMPLOYEE").anyRequest()
        .authenticated()
        .and()
        // .authorizeRequests().antMatchers("/user/api/v1/**").permitAll().anyRequest().authenticated()
        // .and()
        .csrf().disable();
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.authenticationProvider(preAuthenticatedAuthenticationProvider());
  }

}
