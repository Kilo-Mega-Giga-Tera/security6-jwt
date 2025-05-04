package boot.app.security;

import boot.app.tuser.model.entity.Tuser;
import boot.app.tuser.repository.TuserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SecurityUserService implements UserDetailsService {

  private final TuserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
    Tuser tuser = userRepository.findByUserIdAndDelYn(userId, "N");
    return new SecurityUserDetail(tuser);
  }
}
