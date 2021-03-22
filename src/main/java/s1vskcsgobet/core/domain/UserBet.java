package s1vskcsgobet.core.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@Entity
@Table(name = "user_bets")
public class UserBet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @ManyToOne
    @JoinColumn(name = "bet_id", nullable = false)
    private Bet bet;
    @ManyToOne
    @JoinColumn(name = "winning_team_id", nullable = false)
    private Team winningTeam;
    @Column(name = "winning_team_coefficient", nullable = false)
    private BigDecimal winningTeamCoefficient;
    @Column(name = "amount", nullable = false)
    private BigDecimal amount;
    @Enumerated(value = EnumType.STRING)
    @Column(name = "status", nullable = false)
    private UserBetStatus status;

    public UserBet(User user, Bet bet, Team winningTeam, BigDecimal winningTeamCoefficient, BigDecimal amount, UserBetStatus status) {
        this.user = user;
        this.bet = bet;
        this.winningTeam = winningTeam;
        this.winningTeamCoefficient = winningTeamCoefficient;
        this.amount = amount;
        this.status = status;
    }

}
