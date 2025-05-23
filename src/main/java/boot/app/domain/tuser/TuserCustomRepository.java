package boot.app.domain.tuser;

import org.springframework.stereotype.Repository;

@Repository
public interface TuserCustomRepository {

  Tuser findByUser(String userId, String delYn);
}
