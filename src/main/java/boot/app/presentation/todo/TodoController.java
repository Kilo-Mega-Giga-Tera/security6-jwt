package boot.app.presentation.todo;

import boot.app.application.todo.TodoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api")
public class TodoController {

  private final TodoService todoService;

  @GetMapping("/todo")
  public Page<TodoResponseDto> getTodo(
      @PageableDefault(sort = "updatedAt", direction = Sort.Direction.DESC) Pageable pageable) {
    return todoService.getTodo(pageable);
  }

  @PostMapping("/todo")
  public TodoResponseDto saveTodo(@RequestBody TodoRequestDto todoRequestDto) {
    return todoService.saveTodo(todoRequestDto);
  }

  @DeleteMapping("/todo/{seq}")
  public TodoResponseDto deleteTodo(@PathVariable Long seq) {
    return todoService.todoResponseDto(seq);
  }

  @PutMapping("/todo/{seq}")
  public TodoResponseDto updateTodo(
      @PathVariable Long seq, @Valid @RequestBody TodoRequestDto todoRequestDto) {
    return todoService.updateTodo(seq, todoRequestDto);
  }
}
