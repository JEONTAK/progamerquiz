package pq.progamerquiz.domain.auth.dto.response;

import lombok.Getter;
import pq.progamerquiz.common.enums.UserRole;

@Getter
public class LoginResponse {

    private final Long id;
    private final String userName;
    private final UserRole userRole;

    private LoginResponse(Long id, String userName, UserRole userRole) {
        this.id = id;
        this.userName = userName;
        this.userRole = userRole;
    }

    public static LoginResponse of(Long id, String userName, UserRole userRole) {
        return new LoginResponse(id, userName, userRole);
    }

}
