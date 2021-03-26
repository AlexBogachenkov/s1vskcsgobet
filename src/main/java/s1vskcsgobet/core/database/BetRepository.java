package s1vskcsgobet.core.database;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import s1vskcsgobet.core.domain.Bet;

import java.util.List;

@Repository
public interface BetRepository extends JpaRepository<Bet, Long> {

    List<Bet> findAllByOrderByIdDesc();

    List<Bet> findByIsActiveOrderByIdDesc(boolean isActive);

    boolean existsById(Long id);

    @Query("SELECT COUNT(b) from Bet b WHERE b.id = :id AND (b.teamA.name = :teamName OR b.teamB.name = :teamName)")
    Long countByIdAndTeamName(Long id, String teamName);

}
