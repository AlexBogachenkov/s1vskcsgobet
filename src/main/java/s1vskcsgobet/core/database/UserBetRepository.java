package s1vskcsgobet.core.database;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import s1vskcsgobet.core.domain.UserBet;

import java.util.List;

@Repository
public interface UserBetRepository extends JpaRepository<UserBet, Long> {

    List<UserBet> findByUserIdOrderByIdDesc(Long userId);

}
