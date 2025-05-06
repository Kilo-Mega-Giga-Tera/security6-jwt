package boot.app.common.advice;

import boot.app.common.model.dto.ApiResponse;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalRestControllerAdvice {

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ApiResponse<String>> handleMethodArgumentNotValidException(
      BindException ex) {
    return ResponseEntity.internalServerError()
        .body(
            ApiResponse.error(
                "시스템 에러 발생",
                Objects.requireNonNull(ex.getBindingResult().getFieldError()).getDefaultMessage()));
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ApiResponse<String>> handleAll(Exception ex) {
    return ResponseEntity.internalServerError()
        .body(ApiResponse.error("서버 오류 발생", ex.getMessage()));
  }
}
