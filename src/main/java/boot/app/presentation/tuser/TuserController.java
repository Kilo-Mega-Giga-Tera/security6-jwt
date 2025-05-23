package boot.app.presentation.tuser;

import boot.app.infrastructure.exception.ApiException;
import boot.app.infrastructure.security.jwt.JwtUtils;
import boot.app.application.tuser.TuserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class TuserController {

  private final TuserService tuserService;
  private final AuthenticationManager authenticationManager;

  @PostMapping("/register")
  public TuserResponseDto register(@RequestBody TuserRequestDto tuserRequestDto) {
    return tuserService.register(tuserRequestDto);
  }

  @DeleteMapping("/unregister")
  public TuserResponseDto unRegister(@RequestBody TuserRequestDto tuserRequestDto) {
    return tuserService.unRegister(tuserRequestDto);
  }

  @PostMapping("/login")
  public TokenResponseDto login(
      @RequestBody TuserRequestDto tuserRequestDto, HttpServletResponse response) throws Exception {

    Authentication authentication =
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                tuserRequestDto.getUserId(), tuserRequestDto.getPassword()));

    String name = authentication.getName();
    List<String> roles =
        authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();

    String accessToken = JwtUtils.generateAccessToken(name, roles);
    String refreshToken = JwtUtils.generateRefreshToken(name);

    response.addCookie(setRefreshTokenCookie(refreshToken));

    return new TokenResponseDto(accessToken);
  }

  @PostMapping("/refresh")
  public TokenResponseDto refresh(HttpServletRequest request, HttpServletResponse response)
      throws Exception {

    Cookie[] cookies = request.getCookies();
    String refreshToken = null;

    if (cookies == null) {
      throw new ApiException("토큰갱신에 실패했습니다(1)");
    }

    for (Cookie cookie : cookies) {
      if (cookie.getName().equals("refresh_token")) {
        refreshToken = cookie.getValue();
      }
    }

    if (!JwtUtils.verifyRefreshToken(refreshToken)) {
      throw new ApiException("토큰갱신에 실패했습니다(2)");
    }

    String userId = JwtUtils.extractUserId(refreshToken);

    TuserResponseDto tuserResponseDto = tuserService.findByUserId(userId);

    String accessToken =
        JwtUtils.generateAccessToken(userId, List.of("ROLE_" + tuserResponseDto.getRole()));
    String newRefreshToken = JwtUtils.generateRefreshToken(userId);

    TokenResponseDto tokenResponseDto = new TokenResponseDto(accessToken);
    response.addCookie(setRefreshTokenCookie(newRefreshToken));

    return tokenResponseDto;
  }

  @PostMapping("/logout")
  public Map<String, String> logout(HttpServletResponse response) {
    Cookie cookie = new Cookie("refresh_token", null);
    cookie.setPath("/");
    cookie.setMaxAge(0);
    cookie.setHttpOnly(true);
    cookie.setSecure(false);
    response.addCookie(cookie);

    return Map.of("message", "로그아웃 완료");
  }

  private static Cookie setRefreshTokenCookie(String refreshToken) {
    Cookie cookie = new Cookie("refresh_token", refreshToken);
    cookie.setPath("/");
    cookie.setMaxAge(60 * 60 * 24 * 7);
    cookie.setHttpOnly(true);
    cookie.setSecure(false);
    return cookie;
  }
}
