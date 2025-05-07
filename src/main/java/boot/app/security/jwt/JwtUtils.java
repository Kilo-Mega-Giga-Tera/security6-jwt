package boot.app.security.jwt;

import boot.app.common.aes.AesUtils;
import boot.app.tuser.model.dto.response.ResultMapDto;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Duration;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class JwtUtils {

  private static String password;
  private static StringRedisTemplate redisTemplate;
  private static final Long ACCESS_TOKEN_TIME = 600L;
  private static final Long REFRESH_TOKEN_TIME = 60L * 60L * 24L * 7L;

  JwtUtils(@Value("${jwt.password}") String password, StringRedisTemplate redisTemplate) {
    JwtUtils.password = password;
    JwtUtils.redisTemplate = redisTemplate;
  }

  public static String generateAccessToken(String username, List<String> roles) throws Exception {
    return JWT.create()
        .withIssuer(AesUtils.encrypt(username))
        .withClaim("roles", roles.getFirst())
        .withClaim("type", "access")
        .withIssuedAt(new Date())
        .withExpiresAt(new Date().toInstant().plusSeconds(ACCESS_TOKEN_TIME))
        .sign(Algorithm.HMAC256(password));
  }

  public static String generateRefreshToken(String username) throws Exception {
    String token =
        JWT.create()
            .withIssuer(AesUtils.encrypt(username))
            .withClaim("type", "refresh")
            .withIssuedAt(new Date())
            .withExpiresAt(new Date().toInstant().plusSeconds(REFRESH_TOKEN_TIME))
            .sign(Algorithm.HMAC256(password));
    redisTemplate.opsForValue().set("refresh_token:" + username, token, Duration.ofDays(7));
    return token;
  }

  public static boolean verifyAccessToken(String token) {
    try {
      JWTVerifier verify = JWT.require(Algorithm.HMAC256(password)).build();
      DecodedJWT decodedJWT = verify.verify(token);

      return "access".equals(decodedJWT.getClaim("type").asString());
    } catch (Exception e) {
      log.error("verifyAccessToken - {}", e.getMessage());
      return false;
    }
  }

  public static boolean verifyRefreshToken(String token) {
    try {
      JWTVerifier verify = JWT.require(Algorithm.HMAC256(password)).build();
      DecodedJWT decodedJWT = verify.verify(token);

      if (!"refresh".equals(decodedJWT.getClaim("type").asString())) {
        return false;
      }

      String redisTokenKey = "refresh_token:" + AesUtils.decrypt(decodedJWT.getIssuer());
      String tokenValue = redisTemplate.opsForValue().get(redisTokenKey);

      return Objects.requireNonNull(tokenValue).equals(token);
    } catch (Exception e) {
      log.error("verifyRefreshToken - {}", e.getMessage());
      return false;
    }
  }

  public static String extractUserId(String token) {
    try {
      JWTVerifier verify = JWT.require(Algorithm.HMAC256(password)).build();
      DecodedJWT verifyJwt = verify.verify(token);
      return AesUtils.decrypt(verifyJwt.getIssuer());
    } catch (Exception e) {
      log.error("extractUserId - {}", e.getMessage());
      return "";
    }
  }

  public static String extractUserRole(String token) {
    try {
      JWTVerifier verify = JWT.require(Algorithm.HMAC256(password)).build();
      DecodedJWT decodedJWT = verify.verify(token);
      return decodedJWT.getClaim("roles").asString();
    } catch (Exception e) {
      log.error("extractUserRole - {}", e.getMessage());
      return null;
    }
  }

  public static void verifyFailureHandler(
      HttpServletResponse response, ObjectMapper mapper, String message, int httpStatus)
      throws IOException {
    response.setContentType("application/json");
    response.setCharacterEncoding("UTF-8");
    response.setStatus(httpStatus);

    ResultMapDto<?> errorResponse = new ResultMapDto<>(Map.of("reason", message), "error");
    response.getWriter().write(mapper.writeValueAsString(errorResponse));
  }
}
