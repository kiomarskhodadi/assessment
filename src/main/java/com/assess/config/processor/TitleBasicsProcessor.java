package com.assess.config.processor;

import com.assess.dao.entity.TitleBasics;
import com.assess.service.dto.TitleGenreDto;
import com.assess.service.dto.TitleGenresDto;
import org.springframework.batch.item.ItemProcessor;

import java.util.Arrays;
import java.util.stream.Collectors;

public class TitleBasicsProcessor implements ItemProcessor<TitleBasics, TitleGenresDto> {
@Override
public TitleGenresDto process(TitleBasics item) throws Exception {
        TitleGenresDto retVal = new TitleGenresDto(
                item,
                Arrays.stream(item.getTitleGenres().split(",")).map(genre -> new TitleGenreDto(item.getTconst(),genre)).collect(Collectors.toList()));
        return retVal;
        }
}
