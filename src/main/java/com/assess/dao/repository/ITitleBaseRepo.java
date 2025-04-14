package com.assess.dao.repository;

import com.assess.dao.entity.TitleBasics;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.List;
@Repository
public interface ITitleBaseRepo extends JpaRepository<TitleBasics,Integer> {
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
            " SELECT t.* " +
            " FROM title_basics t " +
            " INNER JOIN title_directors td ON t.tconst = td.tconst " +
            " INNER JOIN Title_Writers tw ON tw.tconst = t.tconst " ,
//            "  WHERE td.DIRECTOR = tw.WRITER ",
            nativeQuery = true)
    List<TitleBasics> getTitleBaseQuestionFirst(PageRequest page);
    @Query(value =
            " select * from (select tb.* from title_basics tb           " +
            " where                                                     " +
            "   tb.tconst in (SELECT b.tconst FROM title_principles b   " +
            "      WHERE b.nconst = :actorFirst                         " +
            "        and b.principle_category = 'actor') )ftb           " +
            "  where                                                    " +
            "    ftb.tconst in (SELECT b.tconst FROM title_principles b " +
            "      WHERE b.nconst = :actorSecond                        " +
            "        and b.principle_category = 'actor')                " ,
            nativeQuery = true)
    List<TitleBasics> getTitleBaseQuestionSecond(@Param(value = "actorFirst") String actorFirst,
                                                 @Param(value = "actorSecond") String actorSecond,
                                                 PageRequest page);

    @Query(value =
            " select r.title_basics_id,                                                                          " +
            "       r.tconst,                                                                                    " +
            "       r.title_type,                                                                                " +
            "       r.primary_title,                                                                             " +
            "       r.original_title,                                                                            " +
            "       r.is_adult,                                                                                  " +
            "       r.start_year,                                                                                " +
            "       r.end_year,                                                                                  " +
            "       r.runtime_minutes,                                                                           " +
            "       r.title_genres                                                                               " +
            "from (                                                                                              " +
            "select tb.*,                                                                                        " +
            "    rank() OVER (PARTITION BY tb.start_year ORDER BY tr.average_rating desc,tr.num_votes desc ) rn  " +
            " from title_basics tb                                                                               " +
            "    inner join title_ratings tr on tb.tconst = tr.tconst                                            " +
            "  where tb.tconst in                                                                                " +
            "       (SELECT g.tconst FROM title_genres g                                                         " +
            "               WHERE g.genres = :genre )) r                                                          " +
            "where r.rn = 1                                                                                      ",
            nativeQuery = true)
    List<TitleBasics> getTitleBaseQuestionThird(@Param(value = "genre") String genre, PageRequest page);
}
