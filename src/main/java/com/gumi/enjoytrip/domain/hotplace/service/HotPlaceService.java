package com.gumi.enjoytrip.domain.hotplace.service;

import com.gumi.enjoytrip.domain.KakaoRestClient;
import com.gumi.enjoytrip.domain.exception.NoSuchPermissionException;
import com.gumi.enjoytrip.domain.hotplace.dto.HotPlaceCreateDto;
import com.gumi.enjoytrip.domain.hotplace.dto.HotPlaceDto;
import com.gumi.enjoytrip.domain.hotplace.dto.HotPlaceListDto;
import com.gumi.enjoytrip.domain.hotplace.dto.HotPlaceUpdateDto;
import com.gumi.enjoytrip.domain.hotplace.entity.HotPlace;
import com.gumi.enjoytrip.domain.hotplace.exception.HotPlaceNotFoundException;
import com.gumi.enjoytrip.domain.hotplace.repository.HotPlaceRepository;
import com.gumi.enjoytrip.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class HotPlaceService {

    private final HotPlaceRepository hotPlaceRepository;
    private final KakaoRestClient kakaoRestClient;

    @Transactional
    public Long createHotPlace(HotPlaceCreateDto hotPlaceCreateDto, MultipartFile imageFile, User user) {
        String address = kakaoRestClient.getAddress(hotPlaceCreateDto.getLongitude(), hotPlaceCreateDto.getLatitude());
        String saveFileName = null;
        try {
            saveFileName = saveImageFile(imageFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        HotPlace hotPlace = hotPlaceCreateDto.toEntity(user, address, saveFileName);
        return hotPlaceRepository.save(hotPlace).getId();
    }

    @Transactional(readOnly = true)
    public List<HotPlaceListDto> getHotPlaceList() {
        return hotPlaceRepository.findAll().stream().map(this::toHotPlaceListDto).toList();
    }

    @Transactional(readOnly = true)
    public HotPlaceDto getHotPlace(long id) {
        HotPlace hotPlace = hotPlaceRepository.findById(id).orElseThrow(() -> new HotPlaceNotFoundException("해당 핫플레이스가 존재하지 않습니다."));
        return toHotPlaceDto(hotPlace);
    }

    @Transactional
    public Long updateHotPlace(long id, HotPlaceUpdateDto hotPlaceUpdateDto, User user) {
        if (id != hotPlaceUpdateDto.getId()) {
            throw new IllegalArgumentException("핫플레이스 id가 일치하지 않습니다.");
        }
        HotPlace hotPlace = hotPlaceRepository.findById(id).orElseThrow(() -> new HotPlaceNotFoundException("해당 핫플레이스가 존재하지 않습니다."));
        if (!Objects.equals(hotPlace.getUser().getId(), user.getId())) {
            throw new NoSuchPermissionException("해당 핫플레이스를 수정할 권한이 없습니다.");
        }
        return hotPlaceRepository.save(hotPlace.update(hotPlaceUpdateDto.toEntity())).getId();
    }

    @Transactional
    public void deleteHotPlace(long id, User user) {
        HotPlace hotPlace = hotPlaceRepository.findById(id).orElseThrow(() -> new HotPlaceNotFoundException("해당 핫플레이스가 존재하지 않습니다."));
        if (!Objects.equals(hotPlace.getUser().getId(), user.getId())) {
            throw new NoSuchPermissionException("해당 핫플레이스를 삭제할 권한이 없습니다.");
        }
        String imageFileName = hotPlace.getImageFileName();
        if (imageFileName != null) {
            try {
                Path filePath = new ClassPathResource("static/hot_place_images").getFile().toPath().resolve(imageFileName);
                filePath.toFile().delete();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        hotPlaceRepository.deleteById(id);
    }

    private String saveImageFile(MultipartFile imageFile) throws IOException {
        String originalFilename = imageFile.getOriginalFilename();
        String fileExtension = StringUtils.getFilenameExtension(originalFilename);
        String newFileName = generateNewFileName(fileExtension);

        // 원본 이미지를 BufferedImage로 읽어옴
        BufferedImage originalImage = ImageIO.read(imageFile.getInputStream());

        // 가로 4 세로 3 비율로 리사이징한 BufferedImage 생성
        BufferedImage resizedImage = resizeImage(originalImage, 800, 600);

        // 저장할 파일 경로 생성
        Path filePath = new ClassPathResource("static/hot_place_images").getFile().toPath().resolve(newFileName);

        // 리사이징된 이미지를 파일로 저장
        ImageIO.write(resizedImage, Objects.requireNonNull(fileExtension), filePath.toFile());

        return newFileName;
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

    private String generateNewFileName(String fileExtension) {
        return UUID.randomUUID().toString() + "." + fileExtension;
    }

    private HotPlaceDto toHotPlaceDto(HotPlace hotPlace) {
        return new HotPlaceDto(
                hotPlace.getId(),
                hotPlace.getName(),
                hotPlace.getContent(),
                hotPlace.getAddress(),
                hotPlace.getPlaceType(),
                hotPlace.getImageFileName(),
                hotPlace.getCreatedAt(),
                hotPlace.getUser().getId(),
                hotPlace.getUser().getImageFileName(),
                hotPlace.getUser().getNickname(),
                hotPlace.getLatitude(),
                hotPlace.getLongitude(),
                hotPlace.getVisitDate()
        );
    }

    private HotPlaceListDto toHotPlaceListDto(HotPlace hotPlace) {
        return new HotPlaceListDto(
                hotPlace.getId(),
                hotPlace.getName(),
                hotPlace.getPlaceType(),
                hotPlace.getImageFileName(),
                hotPlace.getCreatedAt(),
                hotPlace.getUser().getId(),
                hotPlace.getUser().getNickname()
        );
    }
}
