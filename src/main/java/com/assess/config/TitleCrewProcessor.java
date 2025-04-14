package com.assess.config;

import com.assess.dao.entity.TitleDirectors;
import com.assess.dao.entity.TitleDirectorsWritersProcessor;
import com.assess.dao.entity.TitleWriters;
import com.assess.service.dto.TitleCrewDto;
import org.springframework.batch.item.ItemProcessor;
import java.util.*;
import java.util.stream.Collectors;

public class TitleCrewProcessor implements ItemProcessor<TitleCrewDto, TitleDirectorsWritersProcessor> {
    @Override
    public TitleDirectorsWritersProcessor process(TitleCrewDto item) throws Exception {
        TitleDirectorsWritersProcessor retVal = new TitleDirectorsWritersProcessor(
                Arrays.stream(item.getWriters().split(",")).map(writer -> new TitleWriters(item.getTconst(),writer)).collect(Collectors.toList()),
                Arrays.stream(item.getDirectors().split(",")).map(director -> new TitleDirectors(item.getTconst(),director)).collect(Collectors.toList())
        );
        return retVal;
    }
}
