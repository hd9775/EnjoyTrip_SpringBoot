package com.gumi.enjoytrip.domain.participant.service;

import com.gumi.enjoytrip.domain.participant.dto.ParticipantCreateDto;
import com.gumi.enjoytrip.domain.participant.dto.ParticipantListDto;
import com.gumi.enjoytrip.domain.participant.entity.Participant;
import com.gumi.enjoytrip.domain.participant.exception.ParticipantNotFoundException;
import com.gumi.enjoytrip.domain.participant.repository.ParticipantRepository;
import com.gumi.enjoytrip.domain.post.exception.InvalidUserException;
import com.gumi.enjoytrip.domain.recuritment.entity.Recruitment;
import com.gumi.enjoytrip.domain.recuritment.exception.RecruitmentNotFountException;
import com.gumi.enjoytrip.domain.recuritment.repository.RecruitmentRepository;
import com.gumi.enjoytrip.domain.user.entity.User;
import com.gumi.enjoytrip.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ParticipantService {

    private final ParticipantRepository participantRepository;
    private final RecruitmentRepository recruitmentRepository;
    private final UserRepository userRepository;

    public List<ParticipantListDto> getParticipantList(long id, User user) {
        Recruitment recruitment = recruitmentRepository.findById(id).orElseThrow(() -> new RecruitmentNotFountException("존재하지 않는 모집글 입니다."));
        if(!Objects.equals(recruitment.getUser().getId(), user.getId())) {
            throw new InvalidUserException("작성자만 조회할 수 있습니다.");
        }
        return participantRepository.findAllByRecruitmentId(id);
    }

    public long createParticipant(ParticipantCreateDto participantCreateDto, User user) {

        return 0;
    }

    public void deleteParticipant(long id, User user) {
        Participant participant = participantRepository.findById(id).orElseThrow(() -> new ParticipantNotFoundException("존재하지 않는 참여자입니다."));
        Recruitment recruitment = recruitmentRepository.findById(participant.getId()).orElseThrow(() -> new RecruitmentNotFountException("존재하지 않는 모집글 입니다."));
        if(!Objects.equals(recruitment.getUser().getId(), user.getId())) {
            throw new InvalidUserException("참여자만 삭제할 수 있습니다.");
        }
        participantRepository.deleteById(id);
    }

    public void toggleParticipantSelect(long id, User user) {
        
    }
}
