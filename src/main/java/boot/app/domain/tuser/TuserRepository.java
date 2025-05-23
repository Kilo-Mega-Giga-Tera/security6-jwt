package boot.app.domain.tuser;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TuserRepository extends JpaRepository<Tuser, Long>, TuserCustomRepository {}
