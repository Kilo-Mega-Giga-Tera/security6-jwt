package boot.app.tuser.model.dto.request;

import lombok.Data;

@Data
public class TuserRequestDto {

  private String userId;
  private String password;
  private String userName;
}
