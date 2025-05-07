package boot.app.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SecurityUtils {

  public static String getUserId() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    log.info("authentication : {}", authentication);
    return authentication.getName();
  }
}
