package s1vskcsgobet.core.database;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import s1vskcsgobet.core.domain.Bet;

@Repository
public interface BetRepository extends JpaRepository<Bet, Long> {
}
