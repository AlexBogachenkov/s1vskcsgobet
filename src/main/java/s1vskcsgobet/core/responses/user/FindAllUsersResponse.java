package s1vskcsgobet.core.responses.user;

import lombok.Getter;
import s1vskcsgobet.core.domain.User;
import s1vskcsgobet.core.responses.CoreResponse;

import java.util.List;

@Getter
public class FindAllUsersResponse extends CoreResponse {

    private List<User> allUsers;

    public FindAllUsersResponse(List<User> allUsers) {
        this.allUsers = allUsers;
    }
}
