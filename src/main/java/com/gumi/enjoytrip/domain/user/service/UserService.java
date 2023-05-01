package com.gumi.enjoytrip.domain.user.service;

import com.gumi.enjoytrip.domain.user.entity.Role;
import com.gumi.enjoytrip.domain.user.entity.User;
import com.gumi.enjoytrip.domain.user.exception.DuplicateEmailException;
import com.gumi.enjoytrip.domain.user.exception.InvalidPasswordException;
import com.gumi.enjoytrip.domain.user.exception.UserNotFoundException;
import com.gumi.enjoytrip.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User login(String email, String password) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException("사용자를 찾을 수 없습니다."));
        if (!user.getPassword().equals(password)) {
            throw new InvalidPasswordException("비밀번호가 일치하지 않습니다.");
        }
        return user;
    }

    public void signup(String email, String password, String nickname) {
        User user = userRepository.findByEmail(email).orElse(null);
        if (user != null) {
            throw new DuplicateEmailException("중복된 이메일입니다.");
        }
        userRepository.save(User.builder()
                .email(email)
                .password(password)
                .nickname(nickname)
                .role(Role.USER)
                .build());
    }
}
