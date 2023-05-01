package com.gumi.enjoytrip.domain.tourinfo.repository;

import com.gumi.enjoytrip.domain.tourinfo.entity.Gugun;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GugunRepository extends JpaRepository<Gugun, Integer> {
    List<Gugun> findBySidoCode(int sidoCode);
}
