package com.assess.config;

import com.assess.config.mapper.*;
import com.assess.config.processor.TitleBasicsProcessor;
import com.assess.config.processor.TitleCrewProcessor;
import com.assess.config.writer.*;
import com.assess.dao.entity.NameBasics;
import com.assess.dao.entity.TitleBasics;
import com.assess.dao.entity.TitleRatings;
import com.assess.service.dto.TitleCrewDto;
import com.assess.service.dto.TitleDirectorsWritersDto;
import com.assess.service.dto.TitleGenresDto;
import com.assess.service.dto.TitlePrinciplesDto;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.batch.BatchProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.task.TaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;


/**
 * @Creator 4/8/2025
 * @Project IntelliJ IDEA
 * @Author k.khodadi
 **/
@Configuration
@EnableBatchProcessing
@EnableConfigurationProperties(BatchProperties.class)
public class BatchConfig  {
    @Autowired
    private JobRepository jobRepository;
    @Autowired
    private Step importNameBasic;
    @Autowired
    private Step importTitleRatings;
    @Autowired
    private Step importTitleBasic;
    @Autowired
    private Step importTitlePrinciples;
    @Autowired
    private Step importTitleCrews;

    @Bean
    public Job importDataJob() {
        return new JobBuilder("importDataJob", jobRepository)
                .start(importTitleBasic)
                .next(importNameBasic)
                .next(importTitleCrews)
                .next(importTitlePrinciples)
                .next(importTitleRatings)
                .build();
    }




}
