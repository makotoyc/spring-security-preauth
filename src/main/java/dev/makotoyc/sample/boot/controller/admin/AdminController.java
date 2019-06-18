package dev.makotoyc.sample.boot.controller.admin;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/admin/api/v1")
public class AdminController {

  @GetMapping
  public String root() {
    log();
    return "I'm a admin.";
  }

  @GetMapping("/reset")
  public String reset() {
    SecurityContextHolder.clearContext();
    return "reset";
  }

  private void log() {
    log.info("/admin/api/v1");
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    log.info("Auth:");
    if(authentication == null) {
      return;
    }

    authentication.getAuthorities().stream().map(a -> a.getAuthority()).forEach(log::info);
    log.info("Name: " + authentication.getName());
    log.info("Principal: " + authentication.getPrincipal());
    log.info("Detail: " + authentication.getDetails());
  }
}
