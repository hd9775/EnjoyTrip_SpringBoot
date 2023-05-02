package com.gumi.enjoytrip.domain.tourinfo.repository;

import com.gumi.enjoytrip.domain.tourinfo.entity.AttractionInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AttractionInfoRepository extends JpaRepository<AttractionInfo, Integer> {
   List<AttractionInfo> findAllBySidoSidoCodeAndGugunGugunCodeAndContentTypeId(int sidoCode, int gugunCode, int contentTypeId);
}
