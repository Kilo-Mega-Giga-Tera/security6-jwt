package boot.app.domain.tuser;

import lombok.Getter;

@Getter
public enum Roles {
  USER("USER"),
  ADMIN("ADMIN");

  private final String value;

  Roles(String value) {
    this.value = value;
  }
}
