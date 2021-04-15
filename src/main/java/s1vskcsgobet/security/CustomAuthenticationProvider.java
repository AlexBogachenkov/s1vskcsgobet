package s1vskcsgobet.security;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;
import s1vskcsgobet.core.domain.User;
import s1vskcsgobet.core.services.UserService;

import java.util.Collection;
import java.util.Optional;

import static java.util.Collections.singletonList;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final UserService userService;

    public CustomAuthenticationProvider(UserService userService) {
        this.userService = userService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();
        Optional<User> foundUser = userService.findUser(username);

        if (foundUser.isPresent() && BCrypt.checkpw(password, foundUser.get().getPassword())) {
            return createAuthentication(foundUser.get(), password);
        } else {
            throw new BadCredentialsException("Username or password is invalid");
        }
//        return userService.findUser(username, password)
//                .map(user -> createAuthentication(user, password))
//                .orElseThrow(() -> new BadCredentialsException("Username or password is invalid"));
    }

    private Authentication createAuthentication(User user, String password) {
        Collection<GrantedAuthority> grantedAuthorities = getGrantedAuthorities(user);
        return new UsernamePasswordAuthenticationToken(
                new BetsUser(user.getId(), user.getNickname(), password, grantedAuthorities),
                password, grantedAuthorities);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

    private Collection<GrantedAuthority> getGrantedAuthorities(User user) {
        return singletonList(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()));
    }

}
