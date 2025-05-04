package boot.app.tuser.model.entity;

import boot.app.tuser.model.enums.Roles;
import boot.app.tuser.repository.TuserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class TuserTest {

  @Autowired private TuserRepository tuserRepository;

  @Test
  void save() {
    Tuser tuser =
        Tuser.builder().userId("test").userName("test").password("1234").role(Roles.USER).build();
    Tuser savedTuser = tuserRepository.save(tuser);

    Assertions.assertEquals(savedTuser, tuser);
  }
}
