package com.assess.config.step;

import com.assess.config.mapper.TitleCrewMapper;
import com.assess.config.processor.TitleCrewProcessor;
import com.assess.config.writer.TitleDirectorsWritersWriters;
import com.assess.service.dto.TitleCrewDto;
import com.assess.service.dto.TitleDirectorsWritersDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.file.DefaultBufferedReaderFactory;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.task.TaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Configuration
@EnableBatchProcessing
@Slf4j
public class ImportTitleCrew {
    @Autowired
    private JobRepository jobRepository;
    @Autowired
    private PlatformTransactionManager transactionManager;
    @Autowired
    private TaskExecutor taskExecutorStep;
    @Autowired
    private TaskExecutor taskExecutorSlaveStep;

    @Bean
    public FlatFileItemReader<TitleCrewDto> readerTitleCrew() {
        return new FlatFileItemReaderBuilder<TitleCrewDto>()
                .name("titleCrewReader")
                .resource(new FileSystemResource(getClass().getClassLoader().getResource("title.crew.tsv").getPath()))
                .linesToSkip(1)
                .delimited()
                .delimiter("\t")
                .quoteCharacter('ه')
                .names("tconst", "directors", "writers")
                .fieldSetMapper(new TitleCrewMapper())
                .bufferedReaderFactory(new DefaultBufferedReaderFactory() {
                    @Override
                    public BufferedReader create(Resource resource, String encoding) throws IOException {
                        return new BufferedReader(new InputStreamReader(resource.getInputStream(), encoding), 1024*1024*10);
                    }
                })
                .skippedLinesCallback(line -> log.warn("Skipped line: {}", line))
                .saveState(false)
                .build();
    }
    @Bean
    public TitleCrewProcessor processor() {
        return new TitleCrewProcessor();
    }
    @Bean
    public TitleDirectorsWritersWriters writer() {
        return new TitleDirectorsWritersWriters();
    }
    @Bean
    public Step importTitleCrews() {
        return new StepBuilder("importTitleCrew", jobRepository)
                .<TitleCrewDto, TitleDirectorsWritersDto>chunk(2000,transactionManager)
                .reader(readerTitleCrew())
                .processor(processor())
                .writer(writer())
                .faultTolerant()
//                .taskExecutor(taskExecutorStep)
                .build();
    }
}
