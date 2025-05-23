package boot.app.infrastructure.security;

import boot.app.domain.tuser.Tuser;
import java.util.Collection;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@RequiredArgsConstructor
public class SecurityUserDetail implements UserDetails {

  private final Tuser tuser;

  @Override
  public String getUsername() {
    return tuser.getUserId();
  }

  @Override
  public String getPassword() {
    return tuser.getPassword();
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of(new SimpleGrantedAuthority("ROLE_" + tuser.getRole()));
  }
}
