package s1vskcsgobet.core.domain;

public enum Permission {

    USERS_READ("users:read"),
    USERS_CREATE("users:create"),
    USERS_DELETE("users:delete"),
    TEAMS_READ("teams:read"),
    TEAMS_CREATE("teams:create"),
    TEAMS_DELETE("teams:delete"),
    BETS_READ("bets:read"),
    BETS_CREATE("bets:create"),
    BETS_DELETE("bets:delete"),
    USER_BETS_READ("userBets:read"),
    USER_BETS_CREATE("userBets:create"),
    USER_BETS_DELETE("userBets:delete");

    private final String permission;

    Permission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }

}
