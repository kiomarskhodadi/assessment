package com.assess.controller;

import com.assess.common.exception.BusinessCodeException;
import com.assess.common.form.OutputAPIForm;
import com.assess.common.message.IMessageBundle;
import com.assess.service.dto.TitleAttributeDto;
import com.assess.service.dto.TitleBasicsDto;
import com.assess.service.sevices.IFileDataSrv;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * @Creator 4/16/2025
 * @Project IntelliJ IDEA
 * @Author k.khodadi
 **/



@Slf4j
@RestController
@RequestMapping("/api/csv")
public class FilePureAPI {
    @Autowired
    private IFileDataSrv fileDataSrv;
    @Autowired
    private IMessageBundle messageBundle;

    @GetMapping("/load")
    public ResponseEntity<OutputAPIForm> load() {
        OutputAPIForm retVal = new OutputAPIForm<>() ;
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/csv/load").toUriString());
        try{
            System.out.println(Calendar.getInstance().getTime());
            fileDataSrv.readAllFiles();
            System.out.println(Calendar.getInstance().getTime());
        }catch (Exception e){
            log.error("Undefined error in read Files",e);
            retVal.setSuccess(false);
            retVal.getErrors().add(BusinessCodeException.UNDEFINED);
        }
        messageBundle.createMsg(retVal);
        return ResponseEntity.created(uri).body(retVal);
    }
    @GetMapping("/first/search")
    public ResponseEntity<OutputAPIForm<List<TitleAttributeDto>>> firstSearch(@RequestParam(required = false) Integer page,
                                                               @RequestParam(required = false) Integer pageSize) {

        OutputAPIForm<List<TitleAttributeDto>> retVal = new OutputAPIForm<>() ;
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/csv/first/search").toUriString());
        try{
            retVal = fileDataSrv.searchFirstQuestion(page == null || page < 0?0:page,pageSize == null || pageSize < 0?10:pageSize);
        }catch (Exception e){
            log.error("Undefined error in call first API",e);
            retVal.setSuccess(false);
            retVal.getErrors().add(BusinessCodeException.UNDEFINED);
        }
        messageBundle.createMsg(retVal);
        return ResponseEntity.created(uri).body(retVal);
    }
    @GetMapping("/second/search")
    public ResponseEntity<OutputAPIForm<List<TitleAttributeDto>>> secondSearch(@RequestParam String firstActor,
                                                                @RequestParam String secondActor,
                                                                @RequestParam(required = false) Integer page,
                                                                @RequestParam(required = false) Integer pageSize) {

        OutputAPIForm<List<TitleAttributeDto>> retVal = new OutputAPIForm<>() ;
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/csv/first/search").toUriString());
        try{
            if(!StringUtils.hasLength(firstActor) || !StringUtils.hasLength(secondActor)){
                retVal.setSuccess(false);
                retVal.getErrors().add(BusinessCodeException.BAD_PARAMETER);
            }else{
                retVal = fileDataSrv.searchSecondQuestion(firstActor,secondActor,page == null || page < 0?0:page,pageSize == null || pageSize < 0?10:pageSize);
            }
        }catch (Exception e){
            log.error("Undefined error in call first API",e);
            retVal.setSuccess(false);
            retVal.getErrors().add(BusinessCodeException.UNDEFINED);
        }
        messageBundle.createMsg(retVal);
        return ResponseEntity.created(uri).body(retVal);
    }


    @GetMapping("/third/search")
    public ResponseEntity<OutputAPIForm<List<TitleAttributeDto>>> thirdSearch(@RequestParam String genre,
                                                               @RequestParam(required = false) Integer page,
                                                               @RequestParam(required = false) Integer pageSize) {

        OutputAPIForm<List<TitleAttributeDto>> retVal = new OutputAPIForm<>() ;
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/csv/third/search").toUriString());
        try{
            if(!StringUtils.hasLength(genre)){
                retVal.setSuccess(false);
                retVal.getErrors().add(BusinessCodeException.BAD_PARAMETER);
            }else{
                retVal = fileDataSrv.searchThirdQuestion(genre,page == null || page < 0?0:page,pageSize == null || pageSize < 0?10:pageSize);
            }
        }catch (Exception e){
            log.error("Undefined error in call first API",e);
            retVal.setSuccess(false);
            retVal.getErrors().add(BusinessCodeException.UNDEFINED);
        }
        messageBundle.createMsg(retVal);
        return ResponseEntity.created(uri).body(retVal);
    }

}
