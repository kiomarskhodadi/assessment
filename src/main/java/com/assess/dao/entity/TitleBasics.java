package com.assess.dao.entity;


import com.assess.dao.convertors.StringListConverter;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Entity
@Table(name = "TITLE_BASICS", indexes = @Index(name = "idx_title_basics_tconst ", columnList = "tconst"))
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TitleBasics {
    @Id
    @Column(name = "TITLE_BASICS_ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TITLE_BASICS_seq")
    @SequenceGenerator(name = "TITLE_BASICS_seq", sequenceName = "TITLE_BASICS_seq", allocationSize = 100)
    private Long TitleBasicsId;
    @Column(name = "TCONST")
    private String tconst;
    @Column(name = "TITLE_TYPE")
    private String titleType;
    @Column(name = "PRIMARY_TITLE",length = 300)
    private String primaryTitle;
    @Column(name = "ORIGINAL_TITLE",length = 300)
    private String originalTitle;
    @Column(name = "IS_ADULT")
    private String isAdult;
    @Column(name = "START_YEAR")
    private Integer startYear;
    @Column(name = "END_YEAR")
    private Integer endYear;
    @Column(name = "RUNTIME_MINUTES")
    private Integer runtimeMinutes;
    @Column(name = "TITLE_GENRES")
    private String titleGenres;


}
