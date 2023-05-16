package com.gumi.enjoytrip.domain.user.service;

import com.gumi.enjoytrip.domain.user.dto.LoginDto;
import com.gumi.enjoytrip.domain.user.dto.UserCreateDto;
import com.gumi.enjoytrip.domain.user.dto.UserDto;
import com.gumi.enjoytrip.domain.user.entity.Role;
import com.gumi.enjoytrip.domain.user.entity.User;
import com.gumi.enjoytrip.domain.user.exception.DuplicateEmailException;
import com.gumi.enjoytrip.domain.user.exception.InvalidPasswordException;
import com.gumi.enjoytrip.domain.user.exception.LoginUserNotFoundException;
import com.gumi.enjoytrip.domain.user.exception.UserNotFoundException;
import com.gumi.enjoytrip.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public User login(LoginDto loginDto, PasswordEncoder passwordEncoder) {
        User user = userRepository.findByEmail(loginDto.getEmail()).orElseThrow(() -> new UserNotFoundException("사용자를 찾을 수 없습니다."));
        if (!passwordEncoder.matches(loginDto.getPassword(), user.getPassword())) {
            throw new InvalidPasswordException("비밀번호가 일치하지 않습니다.");
        }
        return user;
    }

    @Transactional
    public void join(UserCreateDto userCreateDto, PasswordEncoder passwordEncoder) {
        User user = userRepository.findByEmail(userCreateDto.getEmail()).orElse(null);
        if (user != null) {
            throw new DuplicateEmailException("중복된 이메일입니다.");
        }
        userRepository.save(User.builder()
                .email(userCreateDto.getEmail())
                .password(passwordEncoder.encode(userCreateDto.getPassword()))
                .nickname(userCreateDto.getNickname())
                .imageFileName("default.png")
                .role(Role.ROLE_USER)
                .build());
    }

    @Transactional
    public void updateProfileNickname(String nickname, User user) {
        userRepository.findById(user.getId()).orElseThrow(() -> new UserNotFoundException("사용자를 찾을 수 없습니다."));
        userRepository.save(user.update(User.builder().nickname(nickname).build()));
    }

    @Transactional
    public void changePassword(String password, String newPassword, User user, PasswordEncoder passwordEncoder) {
        userRepository.findById(user.getId()).orElseThrow(() -> new UserNotFoundException("사용자를 찾을 수 없습니다."));
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new InvalidPasswordException("비밀번호가 일치하지 않습니다.");
        }
        userRepository.save(user.update(User.builder().password(passwordEncoder.encode(newPassword)).build()));
    }

    @Transactional
    public void deleteUser(User user) {
        userRepository.findById(user.getId()).orElseThrow(() -> new UserNotFoundException("사용자를 찾을 수 없습니다."));
        userRepository.save(user.update(User.builder().isDeleted(true).email("").password("").nickname("탈퇴한 유저").build()));
    }

    public User getLoginUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public UserDto toUserDto(User user) {
        return new UserDto(
                user.getId(),
                user.getEmail(),
                user.getNickname(),
                user.getRole().name()
        );
    }

    public UserDto getMyUser() {
        if (getLoginUser() == null)
            throw new LoginUserNotFoundException("로그인된 사용자가 없습니다.");
        return toUserDto(getLoginUser());
    }

    public void logout() {
        if (SecurityContextHolder.getContext().getAuthentication() == null)
            throw new LoginUserNotFoundException("로그인된 사용자가 없습니다.");
        SecurityContextHolder.clearContext();
    }
}
