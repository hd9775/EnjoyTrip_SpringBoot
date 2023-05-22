package com.gumi.enjoytrip.domain.tourinfo.repository;

import com.gumi.enjoytrip.domain.tourinfo.entity.AttractionInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.w3c.dom.Attr;

import java.util.List;
import java.util.Optional;

@Repository
public interface AttractionInfoRepository extends JpaRepository<AttractionInfo, Integer> {
   List<AttractionInfo> findAllBySidoSidoCodeAndGugunGugunCodeAndContentTypeId(int sidoCode, int gugunCode, int contentTypeId);

   Optional<AttractionInfo> findByContentId(int contentId);
}
