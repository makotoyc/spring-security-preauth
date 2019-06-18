package dev.makotoyc.sample.boot.security.web.config;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

public class JsonAccessDeniedHandler implements AccessDeniedHandler {

  @Override
  public void handle(HttpServletRequest request, HttpServletResponse response,
      AccessDeniedException accessDeniedException) throws IOException, ServletException {
      if (response.isCommitted()) {
          return;
      }

      // TODO: AccessDeniedHandlerからJSONのメッセージを投げる方法を検討する
      response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
      response.setContentType("application/json");
      response.setCharacterEncoding("UTF-8");
      try {
          response.getWriter()
              .write(new JSONObject()
                  .put("status", "error")
                  .put("message", "権限がありません。")
                  .put("messageId", "xxxx-x")
                  .toString());
      } catch (JSONException e) {
          throw new IOException(e);
      }
  }
}
