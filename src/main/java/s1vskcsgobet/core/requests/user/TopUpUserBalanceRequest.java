package s1vskcsgobet.core.requests.user;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TopUpUserBalanceRequest {

    private Long userId;
    private BigDecimal amount;

    public TopUpUserBalanceRequest(Long userId, BigDecimal amount) {
        this.userId = userId;
        this.amount = amount;
    }

}
