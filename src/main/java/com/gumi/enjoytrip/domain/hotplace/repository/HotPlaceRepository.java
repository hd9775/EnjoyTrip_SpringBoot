package com.gumi.enjoytrip.domain.hotplace.repository;

import com.gumi.enjoytrip.domain.hotplace.entity.HotPlace;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface HotPlaceRepository extends JpaRepository<HotPlace, Long> {

    @Modifying
    @Query("update HotPlace h set h.views = h.views + 1 where h.id = :id")
    void increaseViews(long id);

    int countByUserId(long id);

    int countByNameContainingIgnoreCase(String keyword);

    List<HotPlace> findAllByNameContainingIgnoreCaseOrderByIdDesc(Pageable pageable, String keyword);
}
