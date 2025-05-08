package pq.progamerquiz.domain.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pq.progamerquiz.common.enums.UserRole;
import pq.progamerquiz.common.exception.CustomException;
import pq.progamerquiz.domain.user.entity.User;
import pq.progamerquiz.domain.user.repository.UserRepository;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User signup(String username, String firstPassword, String secondPassword) {
        if (!firstPassword.equals(secondPassword)) {
            throw new CustomException(BAD_REQUEST, "비밀번호가 일치하지 않습니다.");
        }

        if (userRepository.existsByUsername(username)) {
            throw new CustomException(BAD_REQUEST, "이미 존재하는 유저 이름 입니다.");
        }

        User newUser = User.create(username, passwordEncoder.encode(firstPassword), UserRole.USER);
        userRepository.save(newUser);
        return newUser;
    }

}
