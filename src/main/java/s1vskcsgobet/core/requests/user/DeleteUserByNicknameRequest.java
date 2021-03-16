package s1vskcsgobet.core.requests.user;

import lombok.Data;

@Data
public class DeleteUserByNicknameRequest {

    private String nickname;

    public DeleteUserByNicknameRequest(String nickname) {
        this.nickname = nickname;
    }

}
