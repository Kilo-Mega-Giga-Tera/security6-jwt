package boot.app.presentation.todo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@SuppressWarnings("unused")
public class ResultMapDto<T> {

  private T result;
  private String message;

  @JsonInclude(JsonInclude.Include.NON_DEFAULT)
  private int size;

  public ResultMapDto(T result, String message) {
    this.message = message;
    this.result = result;
  }

  public ResultMapDto(T result, String message, int size) {
    this.result = result;
    this.message = message;
    this.size = size;
  }
}
