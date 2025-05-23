package boot.app.domain.todo;

import static boot.app.domain.todo.QTodo.todo;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

@RequiredArgsConstructor
public class TodoCustomRepositoryImpl implements TodoCustomRepository {

  private final JPAQueryFactory query;

  @Override
  public Page<Todo> findByTodoList(String userId, String delYn, Pageable pageable) {
    List<Todo> todoList =
        query
            .selectFrom(todo)
            .where(todo.createdBy.eq(userId), todo.delYn.eq(delYn))
            .orderBy(todo.updatedAt.desc())
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();

    JPAQuery<Long> total =
        query
            .select(todo.count())
            .from(todo)
            .where(todo.createdBy.eq(userId), todo.delYn.eq(delYn));

    return new PageImpl<>(todoList, pageable, Optional.ofNullable(total.fetchOne()).orElse(0L));
  }

  @Override
  public Todo findBySeqDsl(Long seq) {
    return query.selectFrom(todo).where(todo.seq.eq(seq)).fetchOne();
  }
}
