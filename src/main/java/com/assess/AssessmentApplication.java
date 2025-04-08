package com.assess;

import com.assess.service.sevices.CsvService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@Slf4j
@SpringBootApplication
public class AssessmentApplication {

	public static void main(String[] args) {
		ApplicationContext applicationContext = SpringApplication.run(AssessmentApplication.class, args);
		CsvService service = applicationContext.getBean(CsvService.class);
		service.loadCsvFiles("title.basics.tsv");
	}

}
