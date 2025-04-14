package com.assess.config;

import com.assess.dao.entity.*;
import com.assess.dao.repository.INameBasicsRepo;
import com.assess.dao.repository.ITitleBaseRepo;
import com.assess.service.dto.TitleCrewDto;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.boot.autoconfigure.batch.BatchProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.concurrent.ThreadPoolExecutor;


/**
 * @Creator 4/8/2025
 * @Project IntelliJ IDEA
 * @Author k.khodadi
 **/


@Configuration
@EnableBatchProcessing
@EnableConfigurationProperties(BatchProperties.class)
public class BatchConfig  {
    public final INameBasicsRepo nameBasicsRepo;
    public final ITitleBaseRepo titleBaseRepo;
    public final JobRepository jobRepository;
    public final EntityManagerFactory entityManagerFactory;
    public final PlatformTransactionManager transactionManager;

    public BatchConfig(INameBasicsRepo nameBasicsRepo,
                       ITitleBaseRepo titleBaseRepo,
                       JobRepository jobRepository,
                       EntityManagerFactory entityManagerFactory,
                       PlatformTransactionManager transactionManager) {

        this.nameBasicsRepo = nameBasicsRepo;
        this.titleBaseRepo = titleBaseRepo;
        this.jobRepository = jobRepository;
        this.entityManagerFactory = entityManagerFactory;
        this.transactionManager = transactionManager;
    }

    public  ResourceCleanupListener cleanUp(){
        ResourceCleanupListener retVal = new ResourceCleanupListener(entityManagerFactory);
        return retVal;
    }

    @Bean
    public TaskExecutor taskExecutorStep() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(10);
        executor.setMaxPoolSize(20);
        executor.setQueueCapacity(20);
        executor.setThreadNamePrefix("Step-thread-");
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
            .build();
        return retVal;
    }
    @Bean
    public TitleBasicsProcessor titleBasicsProcessor() {
        return new TitleBasicsProcessor();
    }
    @Bean
    public TitleBaseGenresWriters writerTitleBasics() {
        return new TitleBaseGenresWriters(entityManagerFactory);
    }
    @Bean
    public Step importTitleBasics() {
        return new StepBuilder("importTitleBasics", jobRepository)
                .<TitleBasics, TitleGenresProcessor>chunk(50, transactionManager)
                .reader(readerTitleBasics())
                .processor(titleBasicsProcessor())
                .writer(writerTitleBasics())
                .taskExecutor(taskExecutorStep())
                .listener(cleanUp())
                .build();
    }


    @Bean
    public FlatFileItemReader<TitlePrinciples> readerTitlePrinciple() {
        FlatFileItemReader<TitlePrinciples> retVal = new FlatFileItemReaderBuilder<TitlePrinciples>()
                .name("titlePrincipalsReader")
                .resource(new FileSystemResource(getClass().getClassLoader().getResource("title.principals.tsv").getPath()))
                .linesToSkip(1)
                .delimited()
                .delimiter("\t")
                .quoteCharacter('ه')
                .names("tconst","ordering","nconst","category","job","characters")
                .fieldSetMapper(new TitlePrinciplesMapper())
                .build();
        return retVal;
    }
    @Bean
    public JpaItemWriter<TitlePrinciples> writerTitlePrinciples() {
        JpaItemWriter<TitlePrinciples> writer = new JpaItemWriter<>();
        writer.setEntityManagerFactory(entityManagerFactory);
        return writer;
    }
    @Bean
    public Step importTitlePrinciple() {
        return new StepBuilder("importTitlePrinciple", jobRepository)
                .<TitlePrinciples, TitlePrinciples>chunk(50, transactionManager)
                .reader(readerTitlePrinciple())
                .writer(writerTitlePrinciples())
                .taskExecutor(taskExecutorStep())
                .listener(cleanUp())
                .build();
    }
    @Bean
    public FlatFileItemReader<TitleRatings> readerTitleRating() {
        FlatFileItemReader<TitleRatings> retVal = new FlatFileItemReaderBuilder<TitleRatings>()
                .name("titlePrincipalsReader")
                .resource(new FileSystemResource(getClass().getClassLoader().getResource("title.ratings.tsv").getPath()))
                .linesToSkip(1)
                .delimited()
                .delimiter("\t")
                .quoteCharacter('ه')
                .names("tconst","averageRating","numVotes")
                .fieldSetMapper(new TitleRatingMapper())
                .build();
        return retVal;
    }
    @Bean
    public JpaItemWriter<TitleRatings> writerTitleRating() {
        JpaItemWriter<TitleRatings> writer = new JpaItemWriter<>();
        writer.setEntityManagerFactory(entityManagerFactory);
        return writer;
    }
    @Bean
    public Step importTitleRating() {
        return new StepBuilder("importTitleRating", jobRepository)
                .<TitleRatings, TitleRatings>chunk(50, transactionManager)
                .reader(readerTitleRating())
                .writer(writerTitleRating())
                .taskExecutor(taskExecutorStep())
                .listener(cleanUp())
                .build();
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
                .build();
        return retVal;
    }
    @Bean
    public JpaItemWriter<NameBasics> writerNameBasics() {
        JpaItemWriter<NameBasics> writer = new JpaItemWriter<>();
        writer.setEntityManagerFactory(entityManagerFactory);
        return writer;
    }
    @Bean
    public Step importNameBasics() {
        return new StepBuilder("importNameBasics", jobRepository)
                .<NameBasics, NameBasics>chunk(50, transactionManager)
                .reader(readerNameBasics())
                .writer(writerNameBasics())
                .taskExecutor(taskExecutorStep())
                .listener(cleanUp())
                .build();
    }
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
                .build();
    }
    @Bean
    public TitleCrewProcessor processor() {
        return new TitleCrewProcessor();
    }
    @Bean
    public TitleDirectorsWritersWriters writer() {
        return new TitleDirectorsWritersWriters(entityManagerFactory);
    }
    @Bean
    public Step importTitleCrew() {
        return new StepBuilder("importTitleCrew", jobRepository)
                .<TitleCrewDto, TitleDirectorsWritersProcessor>chunk(50,transactionManager)
                .reader(readerTitleCrew())
                .processor(processor())
                .writer(writer())
                .listener(cleanUp())
                .build();
    }
    @Bean
    public Job importDataJob() {
        Flow flowNameBasics = new FlowBuilder<Flow>("flowNameBasics")
                .start(importNameBasics())
                .build();
        Flow flowTitleBasics = new FlowBuilder<Flow>("flowTitleBasics")
                .start(importTitleBasics())
                .build();
        Flow flowTitleCrew = new FlowBuilder<Flow>("flowTitleCrew")
                .start(importTitleCrew())
                .build();
        Flow flowTitlePrinciple = new FlowBuilder<Flow>("flowTitlePrinciple")
                .start(importTitlePrinciple())
                .build();
        Flow flowTitleRating = new FlowBuilder<Flow>("flowTitleRating")
                .start(importTitleRating())
                .build();
        Flow splitFlow = new FlowBuilder<Flow>("splitFlow")
                .split(taskExecutorStep())
                .add( flowTitleBasics,flowNameBasics,flowTitleCrew,flowTitlePrinciple,flowTitleRating)
//                .add( flowTitleBasics)
                .build();
        return new JobBuilder("importDataJob", jobRepository)
                .start(splitFlow)

                .end()
                .build();

    }

}
