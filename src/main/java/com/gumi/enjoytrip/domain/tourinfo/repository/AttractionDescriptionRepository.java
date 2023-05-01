package com.gumi.enjoytrip.domain.tourinfo.repository;

import com.gumi.enjoytrip.domain.tourinfo.entity.AttractionDescription;
import com.gumi.enjoytrip.domain.tourinfo.entity.AttractionInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AttractionDescriptionRepository extends JpaRepository<AttractionDescription, Integer> {
    List<AttractionDescription> findByAttractionInfo_Sido_SidoCodeAndAttractionInfo_Gugun_GugunCodeAndAttractionInfo_ContentTypeId(int sidoCode, int gugunCode, int contentTypeId);
}
