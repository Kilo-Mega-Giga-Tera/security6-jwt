package boot.app.domain.todo;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import java.time.LocalDateTime;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@MappedSuperclass
@Getter
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity {

  @CreatedBy
  @Column(name = "CREATED_BY", length = 50, updatable = false)
  private String createdBy;

  @CreatedDate
  @Column(name = "CREATED_AT", updatable = false)
  private LocalDateTime createdAt;

  @LastModifiedBy
  @Column(name = "UPDATED_BY", length = 50)
  private String updatedBy;

  @LastModifiedDate
  @Column(name = "UPDATED_AT")
  private LocalDateTime updatedAt;

  @Column(name = "DEL_YN", length = 1)
  private String delYn = "N";

  public void delete() {
    this.delYn = "Y";
  }
}
