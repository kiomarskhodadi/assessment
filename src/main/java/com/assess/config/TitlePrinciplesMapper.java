package com.assess.config;

import com.assess.common.utilities.GeneralUtility;
import com.assess.dao.entity.TitlePrinciples;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;

@Slf4j
public class TitlePrinciplesMapper implements FieldSetMapper<TitlePrinciples> {
    @Override
    public TitlePrinciples mapFieldSet(FieldSet fieldSet)  {

        TitlePrinciples retVal = new TitlePrinciples();
        try{
            retVal.setTconst(fieldSet.readString("tconst"));
            retVal.setOrdering(GeneralUtility.convertToInteger(fieldSet.readString("ordering")));
            retVal.setNconst(fieldSet.readString("nconst"));
            retVal.setPrincipleCategory(fieldSet.readString("category"));
            retVal.setJob(fieldSet.readString("job"));
            retVal.setCharacters(fieldSet.readString("characters"));
        }catch (Exception e){
            log.error("Error In read : " ,e);
            e.printStackTrace();
            retVal = new TitlePrinciples();
        }
        return retVal;
    }
}
