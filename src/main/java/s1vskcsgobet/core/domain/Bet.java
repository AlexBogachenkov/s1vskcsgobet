package s1vskcsgobet.core.domain;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "bets")
public class Bet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "team_a_id", nullable = false)
    private Team teamA;
    @ManyToOne
    @JoinColumn(name = "team_b_id", nullable = false)
    private Team teamB;
    @Column(name = "coefficient_team_a", nullable = false)
    private BigDecimal coefficientTeamA;
    @Column(name = "coefficient_team_b", nullable = false)
    private BigDecimal coefficientTeamB;
    @Column(name = "is_active", nullable = false)
    private boolean isActive;

}
