package com.assess.dao.repository;

import com.assess.dao.entity.TitleBasics;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.awt.print.Pageable;
import java.util.List;

public interface ITitleBaseRepo extends JpaRepository<TitleBasics,String> {
//    @Query(value =
//            " SELECT tb.*                                                                               " +
//            "  FROM title_crew c                                                                         " +
//            "         INNER JOIN title_basics tb ON c.tconst = tb.tconst                                " +
//            "         CROSS JOIN LATERAL unnest(string_to_array(c.writers, ',')) AS writer (nconst)     " +
//            "         INNER JOIN name_basics nb ON nb.nconst = writer.nconst                            " +
//            " WHERE c.writers IS NOT NULL                                                               " +
//            "  AND c.directors IS NOT NULL                                                              " +
//            "  AND EXISTS (                                                                             " +
//            "        SELECT 1                                                                           " +
//            "        FROM unnest(string_to_array(c.directors, ',')) AS director(nconst)                 " +
//            "        WHERE director.nconst = writer.nconst                                              " +
//            "    )                                                                                      " +
//            "  AND nb.death_year IS NULL                                                                ",
//            nativeQuery = true)
//    List<TitleBasics> getTitleBaseQuestionFirst(PageRequest page);

    @Query(value =
            " SELECT tb.* " +
            " FROM title_crew c " +
            " INNER JOIN title_basics tb ON c.tconst = tb.tconst " +
            " INNER JOIN TABLE(STRING_SPLIT(c.writers, ',')) writer(nconst) ON writer.tconst = c.tconst " +
            " INNER JOIN name_basics nb ON nb.nconst = TRIM(writer.nconst) ",
            nativeQuery = true)
    List<TitleBasics> getTitleBaseQuestionFirst(PageRequest page);
    @Query(value =
            " select tb.* from title_principles t                       " +
            "    inner join  title_basics tb on t.tconst = tb.tconst    " +
            " where t.principle_category = 'actor'                                      " +
            "  and t.nconst = :actorFirst                                               " +
            "  and t.nconst = :actorSecond                                              ",
            nativeQuery = true)
    List<TitleBasics> getTitleBaseQuestionSecond(@Param(value = "actorFirst") String actorFirst,
                                                 @Param(value = "actorSecond") String actorSecond,
                                                 PageRequest page);

    @Query(value =
            " select r.tconst,                                                                                   " +
            "       r.title_type,                                                                                " +
            "       r.primary_title,                                                                             " +
            "       r.original_title,                                                                            " +
            "       r.is_adult,                                                                                  " +
            "       r.start_year,                                                                                " +
            "       r.end_year,                                                                                  " +
            "       r.runtime_minutes,                                                                           " +
            "       r.genres                                                                                     " +
            "from (                                                                                              " +
            "select tb.*,                                                                                        " +
            "    rank() OVER (PARTITION BY tb.start_year ORDER BY tr.average_rating desc,tr.num_votes desc ) rn  " +
            " from title_basics tb                                                               " +
            "    inner join title_ratings tr on tb.tconst = tr.tconst                            " +
            "  where tb.genres = :genre ) r                                                                      " +
            "where rn = 1                                                                                        ",
            nativeQuery = true)
    List<TitleBasics> getTitleBaseQuestionThird(@Param(value = "genre") String genre, PageRequest page);
}
