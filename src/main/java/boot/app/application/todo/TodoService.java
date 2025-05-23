package boot.app.application.todo;

import boot.app.domain.todo.Todo;
import boot.app.domain.todo.TodoRepository;
import boot.app.infrastructure.security.SecurityUtils;
import boot.app.presentation.todo.TodoRequestDto;
import boot.app.presentation.todo.TodoResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TodoService {

  private final TodoRepository todoRepository;

  @Transactional
  public TodoResponseDto saveTodo(TodoRequestDto todoRequestDto) {
    Todo todo =
        Todo.builder()
            .title(todoRequestDto.getTitle())
            .content(todoRequestDto.getContent())
            .build();
    Todo savedTodo = todoRepository.save(todo);

    return TodoResponseDto.builder()
        .seq(savedTodo.getSeq())
        .title(savedTodo.getTitle())
        .createdAt(savedTodo.getCreatedAt())
        .createdBy(savedTodo.getCreatedBy())
        .build();
  }

  public Page<TodoResponseDto> getTodo(Pageable pageable) {
    Page<Todo> todoList = todoRepository.findByTodoList(SecurityUtils.getUserId(), "N", pageable);

    return todoList.map(
        t ->
            TodoResponseDto.builder()
                .seq(t.getSeq())
                .title(t.getTitle())
                .createdBy(t.getCreatedBy())
                .createdAt(t.getCreatedAt())
                .updatedBy(t.getUpdatedBy())
                .updatedAt(t.getUpdatedAt())
                .build());
  }

  @Transactional
  public TodoResponseDto todoResponseDto(Long seq) {
    Todo todo = todoRepository.findBySeqDsl(seq);
    todo.delete();

    return TodoResponseDto.builder()
        .seq(todo.getSeq())
        .title(todo.getTitle())
        .updatedAt(todo.getUpdatedAt())
        .updatedBy(todo.getUpdatedBy())
        .build();
  }

  @Transactional
  public TodoResponseDto updateTodo(Long seq, TodoRequestDto todoRequestDto) {
    Todo todo = todoRepository.findBySeqDsl(seq);
    todo.todoUpdate(todoRequestDto.getTitle(), todoRequestDto.getContent());

    return TodoResponseDto.builder()
        .seq(todo.getSeq())
        .title(todo.getTitle())
        .content(todo.getContent())
        .updatedAt(todo.getUpdatedAt())
        .updatedBy(todo.getUpdatedBy())
        .build();
  }
}
