package com.assess.dao.repository;

import com.assess.dao.entity.TitleBasics;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.awt.print.Pageable;
import java.util.List;

public interface ITitleBaseRepo extends JpaRepository<TitleBasics,String> {
    @Query(value = "select tb from Ti tb inner join title_crew tc on tb.tconst = tc.tconst where  tc.directors @> tc.writers ", nativeQuery = true)
    List<TitleBasics> getTitleBaseQuestionFirst(PageRequest page);
}
