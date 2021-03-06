package s1vskcsgobet.core.requests.user;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class AddUserRequest {

    private String userNickname;
    private String userPassword;
    private BigDecimal userBalance;
    private String userRole;

}
