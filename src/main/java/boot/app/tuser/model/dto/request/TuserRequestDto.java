package boot.app.tuser.model.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TuserRequestDto {

  @NotNull(message = "userId은(는) 필수 값입니다.")
  private String userId;

  private String password;

  private String userName;
}
