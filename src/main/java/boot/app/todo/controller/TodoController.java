package boot.app.todo.controller;

import boot.app.todo.model.dto.request.TodoRequestDto;
import boot.app.todo.model.dto.response.ResultMapDto;
import boot.app.todo.model.dto.response.TodoResponseDto;
import boot.app.todo.service.TodoService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api")
public class TodoController {

  private final TodoService todoService;

  @GetMapping("/todo")
  public ResponseEntity<?> getTodo() {
    List<TodoResponseDto> todoResponseListDto = todoService.getTodo();
    return ResponseEntity.ok()
        .body(new ResultMapDto<>(todoResponseListDto, "success", todoResponseListDto.size()));
  }

  @PostMapping("/todo")
  public ResponseEntity<?> saveTodo(@RequestBody TodoRequestDto todoRequestDto) {
    TodoResponseDto todoResponseDto = todoService.saveTodo(todoRequestDto);
    return ResponseEntity.ok().body(new ResultMapDto<>(todoResponseDto, "success"));
  }

  @DeleteMapping("/todo/{seq}")
  public ResponseEntity<?> deleteTodo(@PathVariable Long seq) {
    TodoResponseDto todoResponseDto = todoService.todoResponseDto(seq);
    return ResponseEntity.ok().body(new ResultMapDto<>(todoResponseDto, "success"));
  }
}
