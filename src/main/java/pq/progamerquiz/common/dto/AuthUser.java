package pq.progamerquiz.common.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pq.progamerquiz.common.enums.UserRole;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AuthUser {

    private Long id;
    private String username;
    private UserRole userRole;

    private AuthUser(Long id, String username, UserRole userRole) {
        this.id = id;
        this.username = username;
        this.userRole = userRole;
    }

    public static AuthUser create(Long id, String username, UserRole userRole) {
        return new AuthUser(id, username, userRole);
    }
}
