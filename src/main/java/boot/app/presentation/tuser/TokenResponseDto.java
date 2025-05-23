package boot.app.presentation.tuser;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TokenResponseDto {

  @JsonProperty("access_token")
  private String accessToken;
}
