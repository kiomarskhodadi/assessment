package com.assess.config.mapper;

import com.assess.service.dto.TitleCrewDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;

@Slf4j
public class TitleCrewMapper implements FieldSetMapper<TitleCrewDto> {
    @Override
    public TitleCrewDto mapFieldSet(FieldSet fieldSet)  {

        TitleCrewDto retVal = new TitleCrewDto();
        try{
            retVal.setTconst(fieldSet.readString("tconst"));
            retVal.setDirectors(fieldSet.readString("directors"));
            retVal.setWriters(fieldSet.readString("writers"));
        }catch (Exception e){
            log.error("Error In read : " ,e);
            e.printStackTrace();
            retVal = new TitleCrewDto();
        }
        return retVal;
    }
}
