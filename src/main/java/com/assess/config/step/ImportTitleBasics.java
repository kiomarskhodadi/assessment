package com.assess.config.step;

import com.assess.config.mapper.TitleBasicsMapper;
import com.assess.config.processor.TitleBasicsProcessor;
import com.assess.config.writer.TitleBaseGenresWriters;
import com.assess.dao.entity.TitleBasics;
import com.assess.service.dto.TitleGenresDto;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.task.TaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableBatchProcessing
public class ImportTitleBasics {
    @Autowired
    private JobRepository jobRepository;
    @Autowired
    private PlatformTransactionManager transactionManager;
    @Autowired
    private TaskExecutor taskExecutorStep;
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
        return new TitleBaseGenresWriters();
    }
    @Bean
    public Step importTitleBasic() {
        return new StepBuilder("importTitleBasics", jobRepository)
                .<TitleBasics, TitleGenresDto>chunk(2000, transactionManager)
                .reader(readerTitleBasics())
                .processor(titleBasicsProcessor())
                .writer(writerTitleBasics())
                .taskExecutor(taskExecutorStep)
                .build();
    }
}
