package pq.progamerquiz.domain.auth.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LoginRequest {

    @Schema(description = "유저이름", example = "example")
    @NotBlank(message = "이름 입력은 필수입니다.")
    private String username;

    @Schema(description = "비밀번호", example = "!Password1234")
    @NotBlank(message = "비밀번호 입력은 필수입니다.")
    private String password;

}
