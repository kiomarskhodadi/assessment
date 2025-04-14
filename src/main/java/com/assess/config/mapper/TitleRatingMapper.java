package com.assess.config.mapper;

import com.assess.common.utilities.GeneralUtility;
import com.assess.dao.entity.TitleRatings;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
@Slf4j
public class TitleRatingMapper implements FieldSetMapper<TitleRatings> {
    @Override
    public TitleRatings mapFieldSet(FieldSet fieldSet)  {
        TitleRatings retVal = new TitleRatings();
        try{
            retVal.setTconst(fieldSet.readString("tconst"));
            retVal.setAverageRating(GeneralUtility.convertToFloat(fieldSet.readString("averageRating")));
            retVal.setNumVotes(GeneralUtility.convertToInteger(fieldSet.readString("numVotes")));
        }catch (Exception e){
            log.error("Error In read : " ,e);
            e.printStackTrace();
            retVal = new TitleRatings();
        }
        return retVal;
    }
}
