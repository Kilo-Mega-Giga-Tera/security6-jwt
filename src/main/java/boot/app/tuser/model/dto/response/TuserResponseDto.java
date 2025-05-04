package boot.app.tuser.model.dto.response;

import boot.app.tuser.model.enums.Roles;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TuserResponseDto {

  private String userId;
  private String userName;
  private LocalDateTime createdAt;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private Roles role;
}
