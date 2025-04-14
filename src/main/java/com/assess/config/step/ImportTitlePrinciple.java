package com.assess.config.step;

import com.assess.config.mapper.TitlePrinciplesMapper;
import com.assess.config.writer.TitlePrincipleWriters;
import com.assess.service.dto.TitlePrinciplesDto;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.task.TaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableBatchProcessing
public class ImportTitlePrinciple {
    @Autowired
    private JobRepository jobRepository;
    @Autowired
    private PlatformTransactionManager transactionManager;
    @Autowired
    private TaskExecutor taskExecutorStep;
    @Autowired
    private TaskExecutor taskExecutorSlaveStep;
    @Bean
    public FlatFileItemReader<TitlePrinciplesDto> readerTitlePrinciple() {
        FlatFileItemReader<TitlePrinciplesDto> retVal = new FlatFileItemReaderBuilder<TitlePrinciplesDto>()
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
    public ItemWriter<TitlePrinciplesDto> writerTitlePrinciples() {
        ItemWriter<TitlePrinciplesDto> writer = new TitlePrincipleWriters();
        return writer;
    }
    @Bean
    public Step importTitlePrinciples() {
        return new StepBuilder("importTitlePrinciple", jobRepository)
                .<TitlePrinciplesDto, TitlePrinciplesDto>chunk(2000, transactionManager)
                .reader(readerTitlePrinciple())
                .writer(writerTitlePrinciples())
                .taskExecutor(taskExecutorStep)
                .build();
    }
}
