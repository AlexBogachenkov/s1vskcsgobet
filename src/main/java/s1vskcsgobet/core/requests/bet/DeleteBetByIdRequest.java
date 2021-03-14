package s1vskcsgobet.core.requests.bet;

import lombok.Data;

@Data
public class DeleteBetByIdRequest {

    private Long betId;

    public DeleteBetByIdRequest(Long betId) {
        this.betId = betId;
    }

}
