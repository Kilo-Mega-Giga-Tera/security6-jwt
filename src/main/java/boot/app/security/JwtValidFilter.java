package boot.app.security;

import boot.app.security.jwt.JwtUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
@Component
public class JwtValidFilter extends OncePerRequestFilter {

  @Override
  protected void doFilterInternal(
      HttpServletRequest request,
      @NonNull HttpServletResponse response,
      @NonNull FilterChain filterChain)
      throws ServletException, IOException {

    String accessToken = request.getHeader("Authorization");
    ObjectMapper mapper = new ObjectMapper();

    if (accessToken == null || !accessToken.startsWith("Bearer ")) {
      JwtUtils.verifyFailureHandler(response, mapper, "인증정보를 확인하세요(1)");
      return;
    }

    accessToken = accessToken.replace("Bearer ", "");

    if (!JwtUtils.verifyAccessToken(accessToken)) {
      JwtUtils.verifyFailureHandler(response, mapper, "인증정보를 확인하세요(2)");
      return;
    }

    String userId = JwtUtils.extractUserId(accessToken);
    String roles = JwtUtils.extractUserRole(accessToken);

    Authentication authentication =
        new UsernamePasswordAuthenticationToken(
            userId, null, List.of(new SimpleGrantedAuthority(roles)));

    SecurityContextHolder.getContext().setAuthentication(authentication);
    filterChain.doFilter(request, response);
  }

  protected boolean shouldNotFilter(HttpServletRequest request) {
    return request.getRequestURI().contains("/api/login")
        || request.getRequestURI().contains("/api/refresh");
  }
}
