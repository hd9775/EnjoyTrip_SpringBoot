package com.gumi.enjoytrip.domain.participant.service;

import com.gumi.enjoytrip.domain.participant.dto.ParticipantCreateDto;
import com.gumi.enjoytrip.domain.participant.dto.ParticipantListDto;
import com.gumi.enjoytrip.domain.participant.entity.Participant;
import com.gumi.enjoytrip.domain.participant.exception.ParticipantNotFoundException;
import com.gumi.enjoytrip.domain.participant.repository.ParticipantRepository;
import com.gumi.enjoytrip.domain.post.exception.InvalidUserException;
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
public class ParticipantService {

    private final ParticipantRepository participantRepository;
    private final RecruitmentRepository recruitmentRepository;

    @Transactional(readOnly = true)
    public List<ParticipantListDto> getParticipantList(long id, User user) {
        Recruitment recruitment = recruitmentRepository.findById(id).orElseThrow(() -> new RecruitmentNotFoundException("존재하지 않는 모집글 입니다."));
        if(!Objects.equals(recruitment.getUser().getId(), user.getId())) {
            throw new InvalidUserException("작성자만 조회할 수 있습니다.");
        }
        return participantRepository.findAllByRecruitmentId(id).stream()
                .map(participant -> toParticipantListDto(participant))
                .toList();
    }

    @Transactional
    public void createParticipant(long id, ParticipantCreateDto participantCreateDto, User user) {
        System.out.println("참여");
        if(participantRepository.countByRecruitmentIdAndUserId(id, user.getId()) == 0) {
            Recruitment recruitment = recruitmentRepository.findById(id).orElseThrow(() -> new RecruitmentNotFoundException("존재하지 않는 모집글 입니다."));
            participantRepository.save(participantCreateDto.toEntity(recruitment, user));
        }
    }

    @Transactional
    public void deleteParticipant(long id, User user) {
        Recruitment recruitment = recruitmentRepository.findById(id).orElseThrow(() -> new RecruitmentNotFoundException("존재하지 않는 모집글 입니다."));

        if(!Objects.equals(recruitment.getUser().getId(), user.getId())) {
            throw new InvalidUserException("참여자만 삭제할 수 있습니다.");
        }
        participantRepository.deleteByRecruitmentIdAndUserId(recruitment.getId(), user.getId());
    }

    @Transactional
    public void toggleParticipantSelect(long id, User user) {
        Recruitment recruitment = recruitmentRepository.findById(id).orElseThrow(() -> new RecruitmentNotFoundException("존재하지 않는 모집글 입니다."));
        if(participantRepository.countByRecruitmentIdAndUserId(id, user.getId()) == 0) {
            throw new ParticipantNotFoundException("존재하지 않는 참여자 입니다.");
        }
        if(!Objects.equals(recruitment.getUser().getId(), user.getId())) {
            throw new InvalidUserException("작성자만 수정할 수 있습니다.");
        }
        Participant participant = participantRepository.findByRecruitmentIdAndUserId(id, user.getId());
        System.out.println(participant.getComment());
        participantRepository.save(participant.update(Participant.builder().isSelected(!participant.getIsSelected()).build()));
    }

    public ParticipantListDto toParticipantListDto(Participant participant) {
        return new ParticipantListDto(
                participant.getId(),
                participant.getUser().getId(),
                participant.getUser().getNickname(),
                participant.getComment(),
                participant.getIsSelected()
        );
    }
}
