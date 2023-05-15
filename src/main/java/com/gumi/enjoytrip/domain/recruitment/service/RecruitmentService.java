package com.gumi.enjoytrip.domain.recruitment.service;

import com.gumi.enjoytrip.domain.participant.repository.ParticipantRepository;
import com.gumi.enjoytrip.domain.post.exception.InvalidUserException;
import com.gumi.enjoytrip.domain.recruitment.dto.RecruitmentCreateDto;
import com.gumi.enjoytrip.domain.recruitment.dto.RecruitmentDto;
import com.gumi.enjoytrip.domain.recruitment.dto.RecruitmentListDto;
import com.gumi.enjoytrip.domain.recruitment.dto.RecruitmentUpdateDto;
import com.gumi.enjoytrip.domain.recruitment.entity.Recruitment;
import com.gumi.enjoytrip.domain.recruitment.exception.RecruitmentNotFoundException;
import com.gumi.enjoytrip.domain.recruitment.repository.RecruitmentRepository;
import com.gumi.enjoytrip.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class RecruitmentService {

    private final RecruitmentRepository recruitmentRepository;
    private final ParticipantRepository participantRepository;

    @Transactional(readOnly = true)
    public List<RecruitmentListDto> getRecruitmentList() {
        return recruitmentRepository.findAll().stream()
                .map(recruitment -> toRecruitmentListDto(recruitment, participantRepository.countByRecruitmentId(recruitment.getId())))
                .toList();
    }

    @Transactional(readOnly = true)
    public RecruitmentDto getRecruitment(long id, User user) {
        Recruitment recruitment = recruitmentRepository.findById(id).orElseThrow(() -> new RecruitmentNotFoundException("존재하지 않는 모집글 입니다."));

        int joinCount = participantRepository.countByRecruitmentId(recruitment.getId());
        boolean isJoin = (participantRepository.countByRecruitmentIdAndUserId(recruitment.getId(), user.getId())==0 ? false : true);
        return toRecruitmentDto(recruitment, isJoin, joinCount);
    }

    @Transactional
    public long createRecruitment(RecruitmentCreateDto recruitmentCreateDto, User user) {
        return recruitmentRepository.save(recruitmentCreateDto.toEntity(user)).getId();
    }

    @Transactional
    public long updateRecruitment(long id, RecruitmentUpdateDto recruitmentUpdateDto, User user) {
        Recruitment recruitment = recruitmentRepository.findById(id).orElseThrow(() -> new RecruitmentNotFoundException("존재하지 않는 모집글 입니다."));
        if(!Objects.equals(recruitment.getUser().getId(), user.getId())) {
            throw new InvalidUserException("작성자만 수정할 수 있습니다.");
        }
        return recruitmentRepository.save(recruitment.update(recruitmentUpdateDto.toEntity())).getId();
    }

    @Transactional
    public void deleteRecruitment(long id, User user) {
        Recruitment recruitment = recruitmentRepository.findById(id).orElseThrow(() -> new RecruitmentNotFoundException("존재하지 않는 모집글 입니다."));
        if(!Objects.equals(recruitment.getUser().getId(), user.getId())) {
            throw new InvalidUserException("작성자만 수정할 수 있습니다.");
        }
        recruitmentRepository.deleteById(id);
    }

    public RecruitmentDto toRecruitmentDto(Recruitment recruitment, boolean isJoin, int joinCount) {
        return new RecruitmentDto(
                recruitment.getId(),
                recruitment.getTitle(),
                recruitment.getContent(),
                recruitment.getIsComplete(),
                isJoin,
                recruitment.getMaxCount(),
                joinCount,
                recruitment.getUser().getId(),
                recruitment.getUser().getNickname(),
                recruitment.getCreatedAt(),
                recruitment.getDeadline()
        );
    }

    public RecruitmentListDto toRecruitmentListDto(Recruitment recruitment, int joinCount) {
        return new RecruitmentListDto(
                recruitment.getId(),
                recruitment.getTitle(),
                recruitment.getIsComplete(),
                recruitment.getMaxCount(),
                joinCount,
                recruitment.getUser().getId(),
                recruitment.getUser().getNickname(),
                recruitment.getCreatedAt(),
                recruitment.getDeadline()
        );
    }
}
