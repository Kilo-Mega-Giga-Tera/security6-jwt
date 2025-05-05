package boot.app.todo.controller;

import boot.app.todo.model.dto.request.TodoRequestDto;
import boot.app.todo.model.dto.response.TodoResponseDto;
import boot.app.todo.service.TodoService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api")
public class TodoController {

  private final TodoService todoService;

  @GetMapping("/todo")
  public List<TodoResponseDto> getTodo() {
    return todoService.getTodo();
  }

  @PostMapping("/todo")
  public TodoResponseDto saveTodo(@RequestBody TodoRequestDto todoRequestDto) {
    return todoService.saveTodo(todoRequestDto);
  }

  @DeleteMapping("/todo/{seq}")
  public TodoResponseDto deleteTodo(@PathVariable Long seq) {
    return todoService.todoResponseDto(seq);
  }
}
