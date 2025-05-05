package boot.app.common.model.dto;

import lombok.Data;

@Data
public class ApiResponse<T> {

  private T result;
  private String status;
  private String message;

  public ApiResponse(T result, String status, String message) {
    this.result = result;
    this.status = status;
    this.message = message;
  }

  public static <T> ApiResponse<T> success(T result) {
    return new ApiResponse<>(result, "success", "요청이 성공했습니다");
  }

  public static <T> ApiResponse<T> error(String message, T result) {
    return new ApiResponse<>(result, "error", message);
  }
}
