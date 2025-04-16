package com.assess.config.step;

import com.assess.config.mapper.TitleBasicsMapper;
import com.assess.config.processor.TitleBasicsProcessor;
import com.assess.config.writer.TitleBaseGenresWriters;
import com.assess.dao.entity.TitleBasics;
import com.assess.service.dto.TitleGenresDto;
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
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.ThreadPoolExecutor;

@Configuration
@EnableBatchProcessing
@Slf4j
public class ImportTitleBasics {
    @Autowired
    private JobRepository jobRepository;
    @Autowired
    private PlatformTransactionManager transactionManager;
    @Autowired
    private TaskExecutor taskExecutorStep;

    @Bean
    public TaskExecutor taskExecutorStepTitleBasics() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(8);
        executor.setMaxPoolSize(8);
        executor.setQueueCapacity(20);
        executor.setThreadNamePrefix("step-tb-thread-");
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.setKeepAliveSeconds(2);
        executor.initialize();
        return executor;
    }

    @Bean
    public FlatFileItemReader<TitleBasics> readerTitleBasics() {
        FlatFileItemReader<TitleBasics> retVal = new FlatFileItemReaderBuilder<TitleBasics>()
                .name("titleBasicsReader")
                .resource(new FileSystemResource(getClass().getClassLoader().getResource("title.basics.tsv").getPath()))
                .linesToSkip(1)
                .delimited()
                .delimiter("\t")
                .quoteCharacter('ه')
                .names("tconst", "titleType", "primaryTitle","originalTitle", "isAdult", "startYear","endYear","runtimeMinutes","genres")
                .fieldSetMapper(new TitleBasicsMapper())
                .bufferedReaderFactory(new DefaultBufferedReaderFactory() {
                    @Override
                    public BufferedReader create(Resource resource, String encoding) throws IOException {
                        return new BufferedReader(new InputStreamReader(resource.getInputStream(), encoding), 1024*1024*10);
                    }
                })
                .skippedLinesCallback(line -> log.warn("Skipped line: {}", line))
                .saveState(false)
                .build();
        return retVal;
    }
    @Bean
    public TitleBasicsProcessor titleBasicsProcessor() {
        return new TitleBasicsProcessor();
    }
    @Bean
    public TitleBaseGenresWriters writerTitleBasics() {
        return new TitleBaseGenresWriters();
    }
    @Bean
    public Step importTitleBasic() {
        return new StepBuilder("importTitleBasics", jobRepository)
                .<TitleBasics, TitleGenresDto>chunk(2000, transactionManager)
                .reader(readerTitleBasics())
                .processor(titleBasicsProcessor())
                .writer(writerTitleBasics())
                .faultTolerant()
                .skipLimit(1000)
                .skip(DataIntegrityViolationException.class)
                .taskExecutor(taskExecutorStepTitleBasics())

                .build();
    }
}
