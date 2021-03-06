package s1vskcsgobet.core.responses.user;

import lombok.Getter;
import s1vskcsgobet.core.domain.User;
import s1vskcsgobet.core.responses.CoreError;
import s1vskcsgobet.core.responses.CoreResponse;

import java.util.List;

@Getter
public class AddUserResponse extends CoreResponse {

    private User addedUser;

    public AddUserResponse(List<CoreError> errors) {
        super(errors);
    }

    public AddUserResponse(User addedUser) {
        this.addedUser = addedUser;
    }

}
