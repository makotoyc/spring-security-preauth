package dev.makotoyc.sample.boot.security.web.authentication;

import java.security.Principal;
import javax.servlet.http.HttpServletRequest;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;

public class PreAuthenticatedProcessingFilter extends AbstractPreAuthenticatedProcessingFilter {

  @Override
  protected Object getPreAuthenticatedPrincipal(HttpServletRequest request) {
    Principal principal = () -> request.getHeader("X-AUTH-PRINCIPAL");
    return principal;
  }

  @Override
  protected Object getPreAuthenticatedCredentials(HttpServletRequest request) {
    String credential = request.getHeader("X-CREDENTIAL");
    return credential;
  }

}
