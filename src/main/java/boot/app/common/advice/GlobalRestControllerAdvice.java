package boot.app.common.advice;

import boot.app.common.model.dto.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalRestControllerAdvice {

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ApiResponse<String>> handleAll(Exception ex) {
    return ResponseEntity.internalServerError()
        .body(ApiResponse.error("서버 오류 발생", ex.getMessage()));
  }
}
