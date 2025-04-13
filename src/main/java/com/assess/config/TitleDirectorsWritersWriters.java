package com.assess.config;

import com.assess.dao.entity.TitleDirectors;
import com.assess.dao.entity.TitleDirectorsWritersProcessor;
import com.assess.dao.entity.TitleWriters;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaItemWriter;

import java.util.List;
import java.util.stream.Collectors;

public class TitleDirectorsWritersWriters implements ItemWriter<TitleDirectorsWritersProcessor> {

    private final JpaItemWriter<TitleWriters> titleWritersJpaItemWriter;
    private final JpaItemWriter<TitleDirectors> titleDirectorsJpaItemWriter;

    public TitleDirectorsWritersWriters(EntityManagerFactory emf) {
        titleWritersJpaItemWriter = new JpaItemWriter<>();
        titleWritersJpaItemWriter.setEntityManagerFactory(emf);
        titleDirectorsJpaItemWriter = new JpaItemWriter<>();
        titleDirectorsJpaItemWriter.setEntityManagerFactory(emf);
    }

    @Override
    public void write(Chunk<? extends TitleDirectorsWritersProcessor> items) throws Exception {
        List<TitleWriters> allA = items.getItems().stream()
                .flatMap(comp -> comp.getTitleWriters().stream())
                .collect(Collectors.toList());

        List<TitleDirectors> allB = items.getItems().stream()
                .flatMap(comp -> comp.getTitleDirectors().stream())
                .collect(Collectors.toList());

        titleDirectorsJpaItemWriter.write(new Chunk<TitleDirectors>(allB));
        titleWritersJpaItemWriter.write(new Chunk<TitleWriters>(allA));
    }


}
