package boot.app.infrastructure.audit;

import java.util.Optional;
import lombok.NonNull;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;

public class AuditorAwareImpl implements AuditorAware<String> {

  @Override
  @NonNull
  public Optional<String> getCurrentAuditor() {
    return Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication().getName());
  }
}
