package s1vskcsgobet.security;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import s1vskcsgobet.core.domain.User;
import s1vskcsgobet.core.services.UserService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final UserService userService;

    public CustomAuthenticationProvider(UserService userService) {
        this.userService = userService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        UsernamePasswordAuthenticationToken authenticationToken;
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();
        Optional<User> foundUser = userService.findUser(username, password);

        // Check: BCrypt.checkpw(password, foundUser.get().getPassword())
        if (foundUser.isPresent()) {
            Collection<GrantedAuthority> grantedAuthorities = getGrantedAuthorities(foundUser.get());
            authenticationToken = new UsernamePasswordAuthenticationToken(
                    new org.springframework.security.core.userdetails.User(username, password, grantedAuthorities),
                    password, grantedAuthorities);
        } else {
            throw new BadCredentialsException("Username or password is invalid");
        }
        return authenticationToken;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

    private Collection<GrantedAuthority> getGrantedAuthorities(User user) {
        Collection<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        if (user.getRole().name().equals("USER")) {
            grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        } else if (user.getRole().name().equals("MODERATOR")) {
            grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_MODERATOR"));
        } else if (user.getRole().name().equals("ADMIN")) {
            grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        }
        return grantedAuthorities;
    }

}
