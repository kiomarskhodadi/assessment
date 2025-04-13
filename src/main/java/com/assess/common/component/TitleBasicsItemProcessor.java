package com.assess.common.component;

import com.assess.dao.entity.TitleBasics;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

/**
 * @Creator 4/8/2025
 * @Project IntelliJ IDEA
 * @Author k.khodadi
 **/

@Component
public class TitleBasicsItemProcessor implements ItemProcessor<TitleBasics, TitleBasics> {

    @Override
    public TitleBasics process(TitleBasics item) throws Exception {
        return item;
    }
}
