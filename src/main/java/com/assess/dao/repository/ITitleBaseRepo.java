package com.assess.dao.repository;

import com.assess.dao.entity.TitleBasics;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.awt.print.Pageable;
import java.util.List;

public interface ITitleBaseRepo extends JpaRepository<TitleBasics,String> {
    @Query(value =
            " SELECT tb.*                                                                               " +
            "  FROM title_crew c                                                                        " +
            "         INNER JOIN title_basics tb ON c.tconst = tb.tconst                                " +
            "         CROSS JOIN LATERAL unnest(string_to_array(c.writers, ',')) AS writer (nconst)     " +
            "         INNER JOIN name_basics nb ON nb.nconst = writer.nconst                            " +
            " WHERE c.writers IS NOT NULL                                                               " +
            "  AND c.directors IS NOT NULL                                                              " +
            "  AND EXISTS (                                                                             " +
            "        SELECT 1                                                                           " +
            "        FROM unnest(string_to_array(c.directors, ',')) AS director(nconst)                 " +
            "        WHERE director.nconst = writer.nconst                                              " +
            "    )                                                                                      " +
            "  AND nb.death_year IS NULL                                                                ",
            nativeQuery = true)
    List<TitleBasics> getTitleBaseQuestionFirst(PageRequest page);
}
