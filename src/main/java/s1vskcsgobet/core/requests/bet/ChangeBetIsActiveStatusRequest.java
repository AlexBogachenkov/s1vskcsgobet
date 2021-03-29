package s1vskcsgobet.core.requests.bet;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ChangeBetIsActiveStatusRequest {

    private Long betId;
    private boolean newIsActiveStatus;

}
