package boot.app.domain.tuser;

import static boot.app.domain.tuser.QTuser.tuser;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TuserCustomRepositoryImpl implements TuserCustomRepository {

  private final JPAQueryFactory query;

  @Override
  public Tuser findByUser(String userId, String delYn) {
    return query.selectFrom(tuser).where(tuser.userId.eq(userId), tuser.delYn.eq(delYn)).fetchOne();
  }
}
