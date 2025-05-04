package boot.app.common.advice;

import boot.app.tuser.model.dto.response.ResultMapDto;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalRestControllerAdvice {

  @ExceptionHandler(exception = Exception.class)
  public ResponseEntity<?> exception(Exception e) {
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(new ResultMapDto<>(Map.of("error", e.getMessage()), "error"));
  }
}
