package pq.progamerquiz.domain.auth.dto.response;

import lombok.Getter;
import pq.progamerquiz.common.enums.UserRole;

@Getter
public class SignupResponse {

    private final Long id;
    private final String userName;
    private final UserRole userRole;

    private SignupResponse(Long id, String userName, UserRole userRole) {
        this.id = id;
        this.userName = userName;
        this.userRole = userRole;
    }

    public static SignupResponse of(Long id, String userName, UserRole userRole) {
        return new SignupResponse(id, userName, userRole);
    }
}
