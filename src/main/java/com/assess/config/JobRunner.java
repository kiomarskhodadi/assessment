package com.assess.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * @Creator 4/8/2025
 * @Project IntelliJ IDEA
 * @Author k.khodadi
 **/

@Component
public class JobRunner implements CommandLineRunner {
    @Value("${config.h2-method}")
    private boolean h2Method;

    @Autowired
    public JobRunner(JobLauncher jobLauncher, @Qualifier("importDataJob") Job job) {
        this.jobLauncher = jobLauncher;
        this.importPersonJob = job;
    }


    private JobLauncher jobLauncher;
    private Job importPersonJob;

    @Override
    public void run(String... args) throws Exception {
        if(h2Method){
            jobLauncher.run(importPersonJob, new JobParameters());
        }
    }
}
