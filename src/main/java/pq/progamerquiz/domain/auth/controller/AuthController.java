package pq.progamerquiz.domain.auth.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pq.progamerquiz.domain.auth.dto.request.SignupRequest;
import pq.progamerquiz.domain.auth.dto.response.SignupResponse;
import pq.progamerquiz.domain.auth.service.AuthService;
import pq.progamerquiz.domain.user.entity.User;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "회원가입", description = "회원가입 API 입니다. 유저 이름, 비밀번호를 통해 회원가입이 가능합니다.")
    @PostMapping("/signup")
    public ResponseEntity<SignupResponse> signUp(@Valid @RequestBody SignupRequest signupRequest) {
        User user = authService.signup(signupRequest.getUsername(), signupRequest.getFirstPassword(), signupRequest.getSecondPassword());
        SignupResponse signupResponse = SignupResponse.of(user.getId(), user.getUsername(), user.getUserRole());
        return ResponseEntity.ok(signupResponse);
    }

    @Operation(summary = "로그아웃", description = "로그아웃 API 입니다. 로그아웃시 세션 해제, 쿠키를 삭제 하고 로그아웃합니다.")
    @PostMapping("/logout")
    public ResponseEntity<Void> logout() {
        return ResponseEntity.ok().build();
    }
}
