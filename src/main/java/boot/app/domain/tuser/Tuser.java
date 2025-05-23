package boot.app.domain.tuser;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "TUSER")
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Tuser extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "SEQ")
  private Long seq;

  @Column(name = "USER_ID", length = 50, unique = true, nullable = false)
  private String userId;

  @Column(name = "USER_NAME", length = 100, nullable = false)
  private String userName;

  @Column(name = "PASSWORD", length = 64, nullable = false)
  private String password;

  @Column(name = "ROLE", length = 20, nullable = false)
  @Enumerated(EnumType.STRING)
  private Roles role;
}
