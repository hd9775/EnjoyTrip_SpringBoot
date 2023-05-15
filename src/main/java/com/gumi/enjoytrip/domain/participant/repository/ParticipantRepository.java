package com.gumi.enjoytrip.domain.participant.repository;

import com.gumi.enjoytrip.domain.participant.entity.Participant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParticipantRepository extends JpaRepository<Participant, Long> {
    List<Participant> findAllByRecruitmentId(long id);

    int countByRecruitmentId(long id);

    int countByRecruitmentIdAndUserId(long recruitmentId, long userId);

    void deleteByRecruitmentIdAndUserId(long recruitmentId, long userId);

    Participant findByRecruitmentIdAndUserId(long recruitmentId, long userId);
}
