package s1vskcsgobet.core.requests.bet;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DeleteBetByIdRequest {

    private Long betId;

    public DeleteBetByIdRequest(Long betId) {
        this.betId = betId;
    }

}
