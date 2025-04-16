package com.assess.config.step;

import com.assess.config.mapper.NameBasicsMapper;
import com.assess.config.writer.NameBasicsItemWriters;
import com.assess.dao.entity.NameBasics;
import com.assess.dao.entity.TitleBasics;
import com.assess.service.dto.TitleGenresDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.partition.PartitionHandler;
import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.core.partition.support.SimplePartitioner;
import org.springframework.batch.core.partition.support.TaskExecutorPartitionHandler;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemWriter;
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
public class ImportNameBasics {
    @Autowired
    private JobRepository jobRepository;
    @Autowired
    private PlatformTransactionManager transactionManager;
    @Autowired
    private TaskExecutor taskExecutorStep;


    @Bean
    public TaskExecutor taskExecutorStepNameBasics() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(8);
        executor.setMaxPoolSize(8);
        executor.setQueueCapacity(20);
        executor.setThreadNamePrefix("Step-nb-thread-");
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.setKeepAliveSeconds(2);
        executor.initialize();
        return executor;
    }

    @Bean
    public FlatFileItemReader<NameBasics> readerNameBasics() {
        FlatFileItemReader<NameBasics> retVal = new FlatFileItemReaderBuilder<NameBasics>()
                .name("nameBasicsReader")
                .resource(new FileSystemResource(getClass().getClassLoader().getResource("name.basics.tsv").getPath()))
                .linesToSkip(1)
                .delimited()
                .delimiter("\t")
                .quoteCharacter('ه')
                .names("nconst", "primaryName", "birthYear","deathYear", "primaryProfession", "knownForTitles")
                .fieldSetMapper(new NameBasicsMapper())
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
    public ItemWriter<NameBasics> writerJdbcNameBasics() {
        ItemWriter<NameBasics> retVal =  new NameBasicsItemWriters();
        return retVal;
    }

    @Bean
    public Step importNameBasic() {
        return new StepBuilder("importNameBasics", jobRepository)
                .<NameBasics, NameBasics>chunk(10000, transactionManager)
                .reader(readerNameBasics())
                .writer(writerJdbcNameBasics())
                .faultTolerant()
                .skipLimit(100)
                .skip(DataIntegrityViolationException.class)
                .taskExecutor(taskExecutorStepNameBasics())
                .build();
    }
}
