package boot.app.todo.service;

import boot.app.todo.model.dto.request.TodoRequestDto;
import boot.app.todo.model.dto.response.TodoResponseDto;
import boot.app.todo.model.entity.Todo;
import boot.app.todo.repository.TodoRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        .title(savedTodo.getTitle())
        .createdAt(savedTodo.getCreatedAt())
        .createdBy(savedTodo.getCreatedBy())
        .build();
  }

  public List<TodoResponseDto> getTodo() {
    List<Todo> todoList = todoRepository.findByDelYn("N");

    return todoList.stream()
        .map(
            t -> TodoResponseDto.builder()
                .seq(t.getSeq())
                .title(t.getTitle())
                .createdBy(t.getCreatedBy())
                .createdAt(t.getCreatedAt())
                .updatedBy(t.getUpdatedBy())
                .updatedAt(t.getUpdatedAt())
                .build())
        .toList();
  }
}
