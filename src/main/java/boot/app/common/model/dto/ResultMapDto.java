package boot.app.common.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class ResultMapDto<T> {

  private T result;
  private String message;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private int size;

  public ResultMapDto(T result, String message) {
    this.result = result;
    this.message = message;
  }

  public ResultMapDto(T result, String message, int size) {
    this.result = result;
    this.message = message;
    this.size = size;
  }
}
