package s1vskcsgobet.core.responses.user;

import lombok.Getter;
import s1vskcsgobet.core.responses.CoreError;
import s1vskcsgobet.core.responses.CoreResponse;

import java.util.List;

@Getter
public class DeleteUserByNicknameResponse extends CoreResponse {

    private boolean deleted;

    public DeleteUserByNicknameResponse(List<CoreError> errors) {
        super(errors);
    }

    public DeleteUserByNicknameResponse(boolean deleted) {
        this.deleted = deleted;
    }

}
