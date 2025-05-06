package boot.app.todo.model.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TodoRequestDto {

  @NotNull(message = "title은(는) 필수 값 입니다.")
  private String title;

  private String content;
}
