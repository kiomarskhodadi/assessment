package com.assess.controller;

import com.assess.common.exception.BusinessCodeException;
import com.assess.common.form.OutputAPIForm;
import com.assess.common.message.IMessageBundle;
import com.assess.service.dto.TitleBasicsDto;
import com.assess.service.sevices.ITitleBaseSrv;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.Calendar;

@RestController
@RequestMapping("/v1/question")
@Slf4j
public class AssessmentApi {

    @Autowired
    private ITitleBaseSrv titleBaseSrv;
    @Autowired
    private IMessageBundle messageBundle;
    @Autowired
    private JobLauncher jobLauncher;
    @Autowired
    private Job job;

    @GetMapping("/first")
    public ResponseEntity<OutputAPIForm> getAllTitleBasic(){
        OutputAPIForm<ArrayList<TitleBasicsDto>> retVal = new OutputAPIForm<>() ;
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/v1/question/first").toUriString());
        try{
           retVal = titleBaseSrv.getAllTitleBasics();
        }catch (Exception e){
            log.error("Undefined error in call first API",e);
            retVal.setSuccess(false);
            retVal.getErrors().add(BusinessCodeException.UNDEFINED);
        }
        messageBundle.createMsg(retVal);
        return ResponseEntity.created(uri).body(retVal);
    }
    @GetMapping("/second")
    public ResponseEntity<OutputAPIForm> getTitleBasicActors(@RequestParam(required = false) String actorFirst, @RequestParam(required = false) String actorSecond){
        OutputAPIForm<ArrayList<TitleBasicsDto>> retVal = new OutputAPIForm<>();
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/v1/question/first").toUriString());
        try{
            if(!StringUtils.hasLength(actorFirst) || !StringUtils.hasLength(actorSecond) ){
                retVal.setSuccess(false);
                retVal.getErrors().add(BusinessCodeException.BAD_PARAMETER);
            }else{
                retVal = titleBaseSrv.getAllTitleBasics(actorFirst,actorSecond);
            }
        }catch (Exception e){
            log.error("Undefined error in call second API",e);
            retVal.setSuccess(false);
            retVal.getErrors().add(BusinessCodeException.UNDEFINED);

        }
        messageBundle.createMsg(retVal);
        return ResponseEntity.created(uri).body(retVal);
    }
    @GetMapping("/third")
    public ResponseEntity<OutputAPIForm> getTitleBasicGenre(@RequestParam(required = false) String genre){
        OutputAPIForm<ArrayList<TitleBasicsDto>> retVal = new OutputAPIForm<>();
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/v1/question/third").toUriString());
        try{
            if(!StringUtils.hasLength(genre)){
                retVal.setSuccess(false);
                retVal.getErrors().add(BusinessCodeException.BAD_PARAMETER);
            }else{
                retVal = titleBaseSrv.getAllTitleBasics(genre);
            }
        }catch (Exception e){
            log.error("Undefined error in call third API",e);
            retVal.setSuccess(false);
            retVal.getErrors().add(BusinessCodeException.UNDEFINED);

        }
        messageBundle.createMsg(retVal);
        return ResponseEntity.created(uri).body(retVal);
    }

    @GetMapping("/runJob")
    public BatchStatus load() throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
        JobParameters jobParameters = new JobParametersBuilder()
                .addDate("timestamp", Calendar.getInstance().getTime())
                .toJobParameters();
        JobExecution jobExecution = jobLauncher.run(job, jobParameters);
        while (jobExecution.isRunning()){
            System.out.println("..................");
        }
        return jobExecution.getStatus();
    }

}
