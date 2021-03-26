package s1vskcsgobet.core.database;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import s1vskcsgobet.core.domain.UserBet;
import s1vskcsgobet.core.domain.UserBetStatus;

import java.util.List;

@Repository
public interface UserBetRepository extends JpaRepository<UserBet, Long> {

    List<UserBet> findByUserIdOrderByIdDesc(Long userId);

    List<UserBet> findByBetIdAndStatus(Long betId, UserBetStatus status);

    @Modifying
    @Query("UPDATE UserBet ub SET ub.status = :newStatus WHERE ub.bet.id = :betId AND ub.winningTeam.id = :winningTeamId")
    void updateStatusByBetIdAndWinningTeamName(@Param("betId") Long betId, @Param("winningTeamId") Long winningTeamId,
                              @Param("newStatus") UserBetStatus newStatus);

    @Modifying
    @Query("UPDATE UserBet ub SET ub.status = :newStatus WHERE ub.bet.id = :betId AND ub.winningTeam.id <> :winningTeamId")
    void updateStatusByBetIdAndNotWinningTeamName(@Param("betId") Long betId, @Param("winningTeamId") Long winningTeamId,
                                               @Param("newStatus") UserBetStatus newStatus);

}
