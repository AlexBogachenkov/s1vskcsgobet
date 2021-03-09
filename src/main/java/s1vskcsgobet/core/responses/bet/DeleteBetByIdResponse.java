package s1vskcsgobet.core.responses.bet;

import lombok.Getter;
import s1vskcsgobet.core.responses.CoreError;
import s1vskcsgobet.core.responses.CoreResponse;

import java.util.List;

@Getter
public class DeleteBetByIdResponse extends CoreResponse {

    private boolean deleted;

    public DeleteBetByIdResponse(List<CoreError> errors) {
        super(errors);
    }

    public DeleteBetByIdResponse(boolean deleted) {
        this.deleted = deleted;
    }
}
