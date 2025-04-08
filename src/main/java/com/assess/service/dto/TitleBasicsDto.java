package com.assess.service.dto;

import com.assess.dao.convertors.StringListConverter;
import com.assess.dao.entity.TitleBasics;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;
import static com.assess.common.utilities.GeneralUtility.convertToInteger;

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

    public TitleBasicsDto(String line){
        String[] cells = line.split("\t");
        try{
            if(!cells[0].equals("tconst")){
                tconst = cells[0];
                titleType = cells[1];
                primaryTitle = cells[2];
                originalTitle = cells[3];
                isAdult = cells[4];
                startYear = convertToInteger(cells[5]);
                endYear =   convertToInteger(cells[6]);
                runtimeMinutes =  convertToInteger(cells[7]);
                genres = StringUtils.hasLength(cells[8])?Arrays.asList(cells[8].split(",")): null;
            }
        }catch (Exception e){
            tconst = "";
        }
    }
}
