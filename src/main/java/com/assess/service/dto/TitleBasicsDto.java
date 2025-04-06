package com.assess.service.dto;

import com.assess.dao.convertors.StringListConverter;
import com.assess.dao.entity.TitleBasics;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * @Creator 4/6/2025
 * @Project IntelliJ IDEA
 * @Author k.khodadi
 **/

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TitleBasicsDto {
    private String tconst;
    private String titleType;
    private String primaryTitle;
    private String originalTitle;
    private String isAdult;
    private Integer startYear;
    private Integer endYear;
    private Integer runtimeMinutes;
    private List<String> genres;

    public TitleBasicsDto(TitleBasics ent){
        this(ent.getTconst(),
                ent.getTitleType(),
                ent.getPrimaryTitle(),
                ent.getOriginalTitle(),
                ent.getIsAdult(),
                ent.getStartYear(),
                ent.getEndYear(),
                ent.getRuntimeMinutes(),
                null);
    }
}
