package s1vskcsgobet.security;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Getter
public class BetsUser extends org.springframework.security.core.userdetails.User {

    private final Long id;

    public BetsUser(Long id, String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.id = id;
    }

}
