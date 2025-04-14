package com.assess.config.mapper;

import com.assess.common.utilities.GeneralUtility;
import com.assess.dao.entity.TitleBasics;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;

/**
 * @Creator 4/9/2025
 * @Project IntelliJ IDEA
 * @Author k.khodadi
 **/

@Slf4j
public class TitleBasicsMapper implements FieldSetMapper<TitleBasics> {
    @Override
    public TitleBasics mapFieldSet(FieldSet fieldSet) {
        TitleBasics retVal = new TitleBasics();
        try{
            retVal.setTconst(fieldSet.readString("tconst"));
            retVal.setTitleType(fieldSet.readString("titleType"));
            retVal.setPrimaryTitle(fieldSet.readString("primaryTitle"));
            retVal.setPrimaryTitle(retVal.getPrimaryTitle().substring(0,Math.min(retVal.getPrimaryTitle().length(),250)));
            retVal.setOriginalTitle(fieldSet.readString("originalTitle"));
            retVal.setOriginalTitle(retVal.getOriginalTitle().substring(0,Math.min(retVal.getOriginalTitle().length(),250)));
            retVal.setIsAdult(fieldSet.readString("isAdult"));
            retVal.setStartYear(GeneralUtility.convertToInteger(fieldSet.readString("startYear")));
            retVal.setEndYear(GeneralUtility.convertToInteger(fieldSet.readString("endYear")));
            retVal.setRuntimeMinutes(GeneralUtility.convertToInteger(fieldSet.readString("runtimeMinutes")));
            retVal.setTitleGenres(fieldSet.readString("genres"));
        }catch(Exception e){
            log.error("Error In read : " ,e);
            e.printStackTrace();
            retVal = new TitleBasics();
        }
        return retVal;
    }
}
