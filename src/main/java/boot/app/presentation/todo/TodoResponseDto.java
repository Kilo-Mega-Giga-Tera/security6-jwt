package boot.app.presentation.todo;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class TodoResponseDto {

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private Long seq;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private String title;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private String content;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private LocalDateTime createdAt;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private LocalDateTime updatedAt;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private String createdBy;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private String updatedBy;
}
