package boot.app.tuser.repository;

import boot.app.tuser.model.entity.Tuser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TuserRepository extends JpaRepository<Tuser, Long> {
  Tuser findByUserIdAndDelYn(String userId, String delYn);
}
