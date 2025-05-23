package boot.app.domain.todo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface TodoCustomRepository {

  Page<Todo> findByTodoList(String userId, String delYn, Pageable pageable);

  Todo findBySeqDsl(Long seq);
}
