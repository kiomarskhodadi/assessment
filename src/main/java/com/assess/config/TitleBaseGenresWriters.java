package com.assess.config;

import com.assess.dao.entity.*;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaItemWriter;
import java.util.List;
import java.util.stream.Collectors;

public class TitleBaseGenresWriters implements ItemWriter<TitleGenresProcessor> {

    private final JpaItemWriter<TitleBasics> titleBasicsJpaItemWriter;
    private final JpaItemWriter<TitleGenres> titleGenresJpaItemWriter;

    public TitleBaseGenresWriters(EntityManagerFactory emf) {
        titleBasicsJpaItemWriter = new JpaItemWriter<>();
        titleBasicsJpaItemWriter.setEntityManagerFactory(emf);
        titleGenresJpaItemWriter = new JpaItemWriter<>();
        titleGenresJpaItemWriter.setEntityManagerFactory(emf);
    }

    @Override
    public void write(Chunk<? extends TitleGenresProcessor> items) throws Exception {

        List<TitleBasics> allA = items.getItems().stream().map(comp -> comp.getTitleBasics()).collect(Collectors.toList());
        List<TitleGenres> allB = items.getItems().stream()
                .flatMap(comp -> comp.getTitleGenres().stream())
                .collect(Collectors.toList());

        titleBasicsJpaItemWriter.write(new Chunk<TitleBasics>(allA));
        titleGenresJpaItemWriter.write(new Chunk<TitleGenres>(allB));
    }
}
