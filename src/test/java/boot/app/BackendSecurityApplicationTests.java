package boot.app;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;

@SpringBootTest
class BackendSecurityApplicationTests {

  @Autowired StringRedisTemplate restTemplate;

  @Test
  void contextLoads() {
    restTemplate.opsForValue().set("test", "test");
  }
}
