package boot.app.tuser.service;

import boot.app.tuser.model.dto.request.TuserRequestDto;
import boot.app.tuser.model.dto.response.TuserResponseDto;
import boot.app.tuser.model.entity.Tuser;
import boot.app.tuser.model.enums.Roles;
import boot.app.tuser.repository.TuserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TuserService {

  private final TuserRepository tuserRepository;
  private final PasswordEncoder passwordEncoder;

  @Transactional
  public TuserResponseDto register(TuserRequestDto tuserRequestDto) {
    Tuser tuser =
        Tuser.builder()
            .userId(tuserRequestDto.getUserId())
            .userName(tuserRequestDto.getUserName())
            .password(passwordEncoder.encode(tuserRequestDto.getPassword()))
            .role(Roles.USER)
            .build();
    Tuser savedUser = tuserRepository.save(tuser);

    return TuserResponseDto.builder()
        .userId(savedUser.getUserId())
        .userName(savedUser.getUserName())
        .createdAt(savedUser.getCreatedAt())
        .build();
  }

  public TuserResponseDto findByUserId(String userId) {
    Tuser tuser = tuserRepository.findByUserIdAndDelYn(userId, "N");
    return TuserResponseDto.builder().userName(tuser.getUserName()).role(tuser.getRole()).build();
  }
}
