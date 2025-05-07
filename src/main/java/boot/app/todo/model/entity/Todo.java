package boot.app.todo.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "TODO")
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Todo extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "SEQ")
  private Long seq;

  @Column(name = "TITLE", length = 50, unique = true, nullable = false)
  private String title;

  @Column(name = "CONTENT", nullable = false)
  private String content;

  public void todoUpdate(String title, String content) {
    this.title = title;
    this.content = content == null ? "" : content;
  }
}
