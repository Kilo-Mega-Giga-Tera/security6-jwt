package boot.app.tuser.controller;

import boot.app.security.jwt.JwtUtils;
import boot.app.tuser.model.dto.request.TuserRequestDto;
import boot.app.tuser.model.dto.response.ResultMapDto;
import boot.app.tuser.model.dto.response.TokenResponseDto;
import boot.app.tuser.model.dto.response.TuserResponseDto;
import boot.app.tuser.service.TuserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class TuserController {

  private final TuserService tuserService;
  private final AuthenticationManager authenticationManager;

  @PostMapping("/register")
  public ResponseEntity<?> register(@RequestBody TuserRequestDto tuserRequestDto) {
    TuserResponseDto tuserResponseDto = tuserService.register(tuserRequestDto);
    return ResponseEntity.ok().body(new ResultMapDto<>(tuserResponseDto, "success"));
  }

  @PostMapping("/login")
  public ResponseEntity<?> login(
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
    TokenResponseDto tokenResponseDto = new TokenResponseDto(accessToken);

    return new ResponseEntity<>(new ResultMapDto<>(tokenResponseDto, "success"), HttpStatus.OK);
  }

  @PostMapping("/refresh")
  public ResponseEntity<?> refresh(HttpServletRequest request, HttpServletResponse response)
      throws Exception {

    Cookie[] cookies = request.getCookies();
    String refreshToken = null;

    if (cookies == null) {
      return new ResponseEntity<>(
          new ResultMapDto<>(Map.of("reason", "토큰갱신에 실패했습니다(1)"), "error"), HttpStatus.BAD_REQUEST);
    }

    for (Cookie cookie : cookies) {
      if (cookie.getName().equals("refresh_token")) {
        refreshToken = cookie.getValue();
      }
    }

    if (!JwtUtils.verifyRefreshToken(refreshToken)) {
      return new ResponseEntity<>(
          new ResultMapDto<>(Map.of("reason", "토큰갱신에 실패했습니다(2)"), "error"), HttpStatus.BAD_REQUEST);
    }

    String userId = JwtUtils.extractUserId(refreshToken);

    TuserResponseDto tuserResponseDto = tuserService.findByUserId(userId);

    String accessToken =
        JwtUtils.generateAccessToken(userId, List.of("ROLE_" + tuserResponseDto.getRole()));
    String newRefreshToken = JwtUtils.generateRefreshToken(userId);

    TokenResponseDto tokenResponseDto = new TokenResponseDto(accessToken);
    response.addCookie(setRefreshTokenCookie(newRefreshToken));

    return ResponseEntity.ok().body(new ResultMapDto<>(tokenResponseDto, "success"));
  }

  @PostMapping("/logout")
  public ResponseEntity<?> logout(HttpServletResponse response) {
    Cookie cookie = new Cookie("refresh_token", null);
    cookie.setPath("/");
    cookie.setMaxAge(0);
    cookie.setHttpOnly(true);
    cookie.setSecure(false);
    response.addCookie(cookie);

    return ResponseEntity.ok().body(new ResultMapDto<>(Map.of("message", "로그아웃 완료"), "success"));
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
