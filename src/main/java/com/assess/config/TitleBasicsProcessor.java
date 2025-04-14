package com.assess.config;

import com.assess.dao.entity.TitleBasics;
import com.assess.dao.entity.TitleGenres;
import com.assess.dao.entity.TitleGenresProcessor;
import org.springframework.batch.item.ItemProcessor;

import java.util.Arrays;
import java.util.stream.Collectors;

public class TitleBasicsProcessor implements ItemProcessor<TitleBasics, TitleGenresProcessor> {
@Override
public TitleGenresProcessor process(TitleBasics item) throws Exception {
        TitleGenresProcessor retVal = new TitleGenresProcessor(
                item,
                Arrays.stream(item.getTitleGenres().split(",")).map(genre -> new TitleGenres(item.getTconst(),genre)).collect(Collectors.toList()));
        return retVal;
        }
}
