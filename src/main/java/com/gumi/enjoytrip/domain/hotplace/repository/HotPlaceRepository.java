package com.gumi.enjoytrip.domain.hotplace.repository;

import com.gumi.enjoytrip.domain.hotplace.entity.HotPlace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HotPlaceRepository extends JpaRepository<HotPlace, Long> {

}
