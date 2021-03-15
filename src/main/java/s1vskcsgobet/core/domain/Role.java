package s1vskcsgobet.core.domain;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

public enum Role {

    USER(Set.of(Permission.USER_BETS_CREATE, Permission.BETS_READ)),
    MODERATOR(Set.of(Permission.USERS_READ, Permission.TEAMS_READ, Permission.TEAMS_CREATE, Permission.TEAMS_DELETE,
                     Permission.BETS_READ, Permission.BETS_CREATE, Permission.BETS_DELETE, Permission.USER_BETS_READ)),
    ADMIN(Set.of(Permission.values()));

    private final Set<Permission> permissions;

    Role(Set<Permission> permissions) {
        this.permissions = permissions;
    }

    public Set<Permission> getPermissions() {
        return permissions;
    }

    public Set<SimpleGrantedAuthority> getAuthorities() {
        return getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());
    }

}
