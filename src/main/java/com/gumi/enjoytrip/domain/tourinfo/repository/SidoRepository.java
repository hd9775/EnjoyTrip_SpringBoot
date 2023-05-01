package com.gumi.enjoytrip.domain.tourinfo.repository;

import com.gumi.enjoytrip.domain.tourinfo.entity.Sido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SidoRepository extends JpaRepository<Sido, Integer> {
}
