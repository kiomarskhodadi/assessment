package com.assess;

import com.assess.service.sevices.CsvService;
import com.assess.dao.entity.TitleBasics;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@Slf4j
@EnableBatchProcessing
@SpringBootApplication
public class AssessmentApplication {

	public static void main(String[] args) {
		ApplicationContext applicationContext = SpringApplication.run(AssessmentApplication.class, args);
//		CsvService service = applicationContext.getBean(CsvService.class);
//		service.loadCsvFiles("title.basics.tsv");
//		SpringApplication.run(AssessmentApplication.class, args);

	}


	@Bean
	public TitleBasics titleBasics() {
		return new TitleBasics();
	}

}
