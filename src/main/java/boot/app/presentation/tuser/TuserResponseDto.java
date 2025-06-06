package boot.app.presentation.tuser;

import boot.app.domain.tuser.Roles;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TuserResponseDto {

  private String userId;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private String userName;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private LocalDateTime createdAt;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private LocalDateTime updatedAt;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private Roles role;
}
