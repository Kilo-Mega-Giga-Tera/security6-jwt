package boot.app.infrastructure.exception;

public class ApiException extends RuntimeException {
  public ApiException(String message) {
    super(message);
  }
}
