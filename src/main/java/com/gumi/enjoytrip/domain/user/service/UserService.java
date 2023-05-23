package com.gumi.enjoytrip.domain.user.service;

import com.gumi.enjoytrip.domain.hotplace.repository.HotPlaceRepository;
import com.gumi.enjoytrip.domain.post.repository.CommentRepository;
import com.gumi.enjoytrip.domain.post.repository.PostRepository;
import com.gumi.enjoytrip.domain.user.dto.LoginDto;
import com.gumi.enjoytrip.domain.user.dto.ProfileDto;
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
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final HotPlaceRepository hotPlaceRepository;

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
    public void updateProfileImage(MultipartFile image, User user) {
        String saveFileName = "default.png";
        try {
            saveFileName = saveImageFile(image);
            deleteProfileImage(user.getImageFileName());
        } catch (IOException e) {
            e.printStackTrace();
        }
        userRepository.save(user.update(User.builder().imageFileName(saveFileName).build()));
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
    public void deleteUser(String password, User user, PasswordEncoder passwordEncoder) {
        userRepository.findById(user.getId()).orElseThrow(() -> new UserNotFoundException("사용자를 찾을 수 없습니다."));
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new InvalidPasswordException("비밀번호가 일치하지 않습니다.");
        }
        try {
            deleteProfileImage(user.getImageFileName());
        } catch (IOException e) {
            e.printStackTrace();
        }
        userRepository.save(user.update(User.builder().isDeleted(true).email("").password("").nickname("탈퇴한 유저").imageFileName("default.png").build()));
    }

    @Transactional(readOnly = true)
    public ProfileDto getProfile(long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("사용자를 찾을 수 없습니다."));
        int postCount = postRepository.countByUserId(id);
        int commentCount = commentRepository.countByUserId(id);
        int hotPlaceCount = hotPlaceRepository.countByUserId(id);
        return new ProfileDto(
                user.getId(),
                user.getEmail(),
                user.getNickname(),
                user.getImageFileName(),
                user.getRole().name(),
                user.getCreatedAt(),
                postCount,
                commentCount,
                hotPlaceCount
        );
    }

    public User getLoginUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public UserDto toUserDto(User user) {
        return new UserDto(
                user.getId(),
                user.getEmail(),
                user.getNickname(),
                user.getImageFileName(),
                user.getRole().name()
        );
    }

    public UserDto getMyUser() {
        if (getLoginUser() == null)
            throw new LoginUserNotFoundException("로그인된 사용자가 없습니다.");
        return toUserDto(getLoginUser());
    }

    public void logout() {
        User user = getLoginUser();
        if (SecurityContextHolder.getContext().getAuthentication() == null)
            throw new LoginUserNotFoundException("로그인된 사용자가 없습니다.");
        SecurityContextHolder.clearContext();
        user.updateRefreshToken(null);
        userRepository.save(user);
    }

    private String generateNewFileName(String fileExtension) {
        return UUID.randomUUID().toString() + "." + fileExtension;
    }

    private BufferedImage resizeImage(BufferedImage originalImage, int targetWidth, int targetHeight) {
        int originalWidth = originalImage.getWidth();
        int originalHeight = originalImage.getHeight();

        // 가로 800 세로 600 비율로 조정된 크기 계산
        int resizedWidth, resizedHeight;
        double originalAspectRatio = (double) originalWidth / originalHeight;
        double targetAspectRatio = (double) targetWidth / targetHeight;

        if (originalAspectRatio > targetAspectRatio) {
            resizedWidth = targetWidth;
            resizedHeight = (int) (targetWidth / originalAspectRatio);
        } else {
            resizedWidth = (int) (targetHeight * originalAspectRatio);
            resizedHeight = targetHeight;
        }

        // 리사이징
        BufferedImage resizedImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);
        resizedImage.createGraphics().drawImage(originalImage.getScaledInstance(resizedWidth, resizedHeight, BufferedImage.SCALE_SMOOTH), 0, 0, targetWidth, targetHeight, null);

        return resizedImage;
    }

    private String saveImageFile(MultipartFile imageFile) throws IOException {
        String originalFilename = imageFile.getOriginalFilename();
        String fileExtension = StringUtils.getFilenameExtension(originalFilename);
        String newFileName = generateNewFileName(fileExtension);

        // 원본 이미지를 BufferedImage로 읽어옴
        BufferedImage originalImage = ImageIO.read(imageFile.getInputStream());

        // 가로 4 세로 3 비율로 리사이징한 BufferedImage 생성
        BufferedImage resizedImage = resizeImage(originalImage, 400, 400);

        // 저장할 파일 경로 생성
        Path filePath = new ClassPathResource("static/profile_images").getFile().toPath().resolve(newFileName);

        // 리사이징된 이미지를 파일로 저장
        ImageIO.write(resizedImage, Objects.requireNonNull(fileExtension), filePath.toFile());

        return newFileName;
    }

    private void deleteProfileImage(String fileName) throws IOException {
        if(fileName.equals("default.png")) return;
        Path filePath = new ClassPathResource("static/profile_images").getFile().toPath().resolve(fileName);
        Files.delete(filePath);
    }
}
