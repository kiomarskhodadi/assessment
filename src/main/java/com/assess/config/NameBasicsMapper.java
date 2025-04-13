package com.assess.config;

import com.assess.common.utilities.GeneralUtility;
import com.assess.dao.entity.NameBasics;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;

@Slf4j
public class NameBasicsMapper implements FieldSetMapper<NameBasics> {
    @Override
    public NameBasics mapFieldSet(FieldSet fieldSet)  {
        NameBasics retVal = new NameBasics();
        try{
            retVal.setNconst(fieldSet.readString("nconst"));
            retVal.setPrimaryName(fieldSet.readString("primaryName"));
            retVal.setBirthYear(GeneralUtility.convertToInteger(fieldSet.readString("birthYear")));
            retVal.setDeathYear(GeneralUtility.convertToInteger(fieldSet.readString("deathYear")));
            retVal.setPrimaryProfession(fieldSet.readString("primaryProfession"));
            retVal.setKnownForTitle(fieldSet.readString("knownForTitles"));
        }catch (Exception e){
            log.error("Error In read : " ,e);
            retVal = new NameBasics();
        }
        return retVal;
    }
}
