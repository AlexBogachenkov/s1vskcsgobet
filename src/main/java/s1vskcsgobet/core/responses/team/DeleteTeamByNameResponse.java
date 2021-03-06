package s1vskcsgobet.core.responses.team;

import lombok.Getter;
import s1vskcsgobet.core.responses.CoreError;
import s1vskcsgobet.core.responses.CoreResponse;

import java.util.List;

@Getter
public class DeleteTeamByNameResponse extends CoreResponse {

    private boolean deleted;

    public DeleteTeamByNameResponse(List<CoreError> errors) {
        super(errors);
    }

    public DeleteTeamByNameResponse(boolean deleted) {
        this.deleted = deleted;
    }

}
