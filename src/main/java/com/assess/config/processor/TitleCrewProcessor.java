package com.assess.config.processor;

import com.assess.service.dto.TitleCrewDto;
import com.assess.service.dto.TitleDirectorsDto;
import com.assess.service.dto.TitleDirectorsWritersDto;
import com.assess.service.dto.TitleWritersDto;
import org.springframework.batch.item.ItemProcessor;

import java.util.Arrays;
import java.util.stream.Collectors;

public class TitleCrewProcessor implements ItemProcessor<TitleCrewDto, TitleDirectorsWritersDto> {
    @Override
    public TitleDirectorsWritersDto process(TitleCrewDto item) throws Exception {
        TitleDirectorsWritersDto retVal = new TitleDirectorsWritersDto(
                Arrays.stream(item.getWriters().split(",")).map(writer -> new TitleWritersDto(item.getTconst(),writer)).collect(Collectors.toList()),
                Arrays.stream(item.getDirectors().split(",")).map(director -> new TitleDirectorsDto(item.getTconst(),director)).collect(Collectors.toList())
        );
        return retVal;
    }
}
