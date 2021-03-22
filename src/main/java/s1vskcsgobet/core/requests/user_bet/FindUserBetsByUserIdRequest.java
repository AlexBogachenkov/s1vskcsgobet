package s1vskcsgobet.core.requests.user_bet;

import lombok.Data;

@Data
public class FindUserBetsByUserIdRequest {

    private Long userId;

    public FindUserBetsByUserIdRequest(Long userId) {
        this.userId = userId;
    }

}
