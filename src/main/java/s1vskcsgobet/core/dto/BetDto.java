package s1vskcsgobet.core.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class BetDto {

    private String teamAName;
    private String teamBName;
    private BigDecimal coefficientTeamA;
    private BigDecimal coefficientTeamB;
    private boolean isActive;

    public BetDto() {
    }

    public BetDto(String teamAName, String teamBName, BigDecimal coefficientTeamA, BigDecimal coefficientTeamB, boolean isActive) {
        this.teamAName = teamAName;
        this.teamBName = teamBName;
        this.coefficientTeamA = coefficientTeamA;
        this.coefficientTeamB = coefficientTeamB;
        this.isActive = isActive;
    }
}
