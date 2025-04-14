package com.assess.controller;

import com.assess.common.component.CountHttpRequestFilter;
import com.assess.common.exception.BusinessCodeException;
import com.assess.common.form.OutputAPIForm;
import com.assess.common.message.IMessageBundle;
import com.assess.service.dto.TitleBasicsDto;
import com.assess.service.sevices.ITitleBaseSrv;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.sql.DataSource;
import java.net.URI;
import java.util.ArrayList;

@RestController
@RequestMapping("/v1/question")
@Slf4j
public class AssessmentApi {

    @Autowired
    private ITitleBaseSrv titleBaseSrv;
    @Autowired
    private IMessageBundle messageBundle;
    @Autowired
    private DataSource dataSource;

    @Autowired
    private CountHttpRequestFilter countHttpRequestFilter;

    @GetMapping("/first")
    public ResponseEntity<OutputAPIForm> getAllTitleBasic(@RequestParam(required = false) Integer page,
                                                          @RequestParam(required = false) Integer pageSize){
        OutputAPIForm<ArrayList<TitleBasicsDto>> retVal = new OutputAPIForm<>() ;
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/v1/question/first").toUriString());
        try{
           retVal = titleBaseSrv.getAllTitleBasics(page == null || page < 0?0:page,pageSize == null || pageSize < 0?10:pageSize);
        }catch (Exception e){
            log.error("Undefined error in call first API",e);
            retVal.setSuccess(false);
            retVal.getErrors().add(BusinessCodeException.UNDEFINED);
        }
        messageBundle.createMsg(retVal);
        return ResponseEntity.created(uri).body(retVal);
    }
    @GetMapping("/second")
    public ResponseEntity<OutputAPIForm> getTitleBasicActors(@RequestParam(required = false) String firstActor,
                                                             @RequestParam(required = false) String secondActor,
                                                             @RequestParam(required = false) Integer page,
                                                             @RequestParam(required = false) Integer pageSize){
        OutputAPIForm<ArrayList<TitleBasicsDto>> retVal = new OutputAPIForm<>();
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/v1/question/first").toUriString());
        try{
            if(!StringUtils.hasLength(firstActor) || !StringUtils.hasLength(secondActor) ){
                retVal.setSuccess(false);
                retVal.getErrors().add(BusinessCodeException.BAD_PARAMETER);
            }else{
                retVal = titleBaseSrv.getAllTitleBasics(firstActor,secondActor,page == null || page < 0?0:page,pageSize == null || pageSize < 0?10:pageSize);
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
    public ResponseEntity<OutputAPIForm> getTitleBasicGenre(@RequestParam(required = false) String genre,
                                                            @RequestParam(required = false) Integer page,
                                                            @RequestParam(required = false) Integer pageSize){
        OutputAPIForm<ArrayList<TitleBasicsDto>> retVal = new OutputAPIForm<>();
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/v1/question/third").toUriString());
        try{
            if(!StringUtils.hasLength(genre)){
                retVal.setSuccess(false);
                retVal.getErrors().add(BusinessCodeException.BAD_PARAMETER);
            }else{
                retVal = titleBaseSrv.getAllTitleBasics(genre,page == null || page < 0?0:page,pageSize == null || pageSize < 0?10:pageSize);
            }
        }catch (Exception e){
            log.error("Undefined error in call third API",e);
            retVal.setSuccess(false);
            retVal.getErrors().add(BusinessCodeException.UNDEFINED);

        }
        messageBundle.createMsg(retVal);
        return ResponseEntity.created(uri).body(retVal);
    }

    @GetMapping("/forth")
    public ResponseEntity<OutputAPIForm> questionForth(){
        OutputAPIForm<String> retVal = new OutputAPIForm<>();
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/v1/question/forth").toUriString());
        try{
            retVal.setData("The number of request is: "  + countHttpRequestFilter.getCount());
        }catch (Exception e){
            log.error("Undefined error in call third API",e);
            retVal.setSuccess(false);
            retVal.getErrors().add(BusinessCodeException.UNDEFINED);
        }
        messageBundle.createMsg(retVal);
        return ResponseEntity.created(uri).body(retVal);
    }

    @GetMapping("/pool-status")
    public String poolStatus() {
        HikariDataSource hikariDataSource = (HikariDataSource) dataSource;
        return String.format(
                "Active: %d, Idle: %d, Total: %d, Waiting: %d",
                hikariDataSource.getHikariPoolMXBean().getActiveConnections(),
                hikariDataSource.getHikariPoolMXBean().getIdleConnections(),
                hikariDataSource.getHikariPoolMXBean().getTotalConnections(),
                hikariDataSource.getHikariPoolMXBean().getThreadsAwaitingConnection()
        );
    }


}
