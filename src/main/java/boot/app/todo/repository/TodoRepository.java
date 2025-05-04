package boot.app.todo.repository;

import boot.app.todo.model.entity.Todo;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> {

  List<Todo> findByCreatedByAndDelYn(String userId, String delYn);

  Todo findBySeq(Long seq);
}
