package dev.makotoyc.sample.boot.security.core.userdetails;

import java.security.Principal;
import java.util.Arrays;
import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import dev.makotoyc.sample.boot.security.core.UserAuthority;

public class PreAuthenticatedUserDetailsService
    implements AuthenticationUserDetailsService<PreAuthenticatedAuthenticationToken> {
  
  @Override
  public UserDetails loadUserDetails(PreAuthenticatedAuthenticationToken token)
      throws UsernameNotFoundException {
    Object credentials = token.getCredentials();
    if (credentials.toString() == "") {
      throw new UsernameNotFoundException("USER NOT FOUND");
    }

    Principal principal = (Principal) token.getPrincipal();
    Collection<GrantedAuthority> authorities = null;
    if ("admin".equals(principal.getName())) {
      authorities = Arrays.asList(new UserAuthority("ROLE_EMPLOYEE"));
    } else {
      authorities = Arrays.asList(new UserAuthority("ROLE_OTHER"));
    }

    return new User(token.getPrincipal().toString(), "", authorities);
  }

}
