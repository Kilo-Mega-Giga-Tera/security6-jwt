package boot.app.presentation.tuser;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResultMapDto<T> {

  private T result;
  private String message;
}
