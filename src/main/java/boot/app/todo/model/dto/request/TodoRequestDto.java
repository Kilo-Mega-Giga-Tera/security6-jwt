package boot.app.todo.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TodoRequestDto {

  private String title;
  private String content;
}
