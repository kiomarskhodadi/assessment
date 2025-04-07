package com.assess.dao.entity;


import com.assess.dao.convertors.StringListConverter;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "TITLE_BASICS")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TitleBasics {
    @Id
    @Column(name = "TCONST")
    private String tconst;
    @Column(name = "TITLE_TYPE")
    private String titleType;
    @Column(name = "PRIMARY_TITLE")
    private String primaryTitle;
    @Column(name = "ORIGINAL_TITLE")
    private String originalTitle;
    @Column(name = "IS_ADULT")
    private String isAdult;
    @Column(name = "START_YEAR")
    private Integer startYear;
    @Column(name = "END_YEAR")
    private Integer endYear;
    @Column(name = "RUNTIME_MINUTES")
    private Integer runtimeMinutes;
    @Convert(converter = StringListConverter.class)
    @Column(name = "GENRES")
    private List<String> genres;
}
