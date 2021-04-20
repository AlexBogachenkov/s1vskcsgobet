package s1vskcsgobet.core.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class UserDto {

    private Long id;
    private String nickname;
    private BigDecimal balance;

    public UserDto(Long id, String nickname, BigDecimal balance) {
        this.id = id;
        this.nickname = nickname;
        this.balance = balance;
    }

}
