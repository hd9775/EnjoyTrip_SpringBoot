package com.gumi.enjoytrip.domain.recuritment.repository;

import com.gumi.enjoytrip.domain.recuritment.entity.Recruitment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecruitmentRepository extends JpaRepository<Recruitment, Long> {

}
