package dev.makotoyc.sample.boot.security;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import dev.makotoyc.sample.boot.BootSecurityPreauthApplication;

/**
 * Controllerに送られるリクエストに対する認証を確認するテストクラス.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class AuthorizationTest {

  private MockMvc mvc;

  @Autowired
  private WebApplicationContext context;

  @Before
  public void setUp() throws Exception {
    // SecurityContextHolder.clearContext();
    mvc = MockMvcBuilders.webAppContextSetup(context)
        .apply(SecurityMockMvcConfigurers.springSecurity()).build();
  }

  @Test
  @WithMockUser(username = "admin", roles = {"EMPLOYEE", "USER"})
  public void testAuthorised() throws Exception {
    mvc.perform(get("/admin/api/v1/")).andExpect(status().isOk());
  }

  @Test
  @WithMockUser(username = "user", roles = "USER")
  public void testOtherUser() throws Exception {
    mvc.perform(get("/admin/api/v1/")).andExpect(status().isBadRequest());
  }

  @Test
  @WithMockUser(username = "no-role")
  public void testAdminNotAuthenticated() throws Exception {
    mvc.perform(get("/admin/api/v1/")).andExpect(status().isBadRequest());
  }

  @Test
  @WithMockUser(username = "no-role")
  public void testUserNotAuthenticated() throws Exception {
    mvc.perform(get("/user/api/v1/")).andExpect(status().isOk());
  }
}
