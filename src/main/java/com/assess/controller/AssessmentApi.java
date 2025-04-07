package com.assess.controller;

import com.assess.common.form.OutputAPI;
import com.assess.common.message.IMessageBundle;
import com.assess.dao.entity.TitleBasics;
import com.assess.service.dto.TitleBasicsDto;
import com.assess.service.sevices.ITitleBaseSrv;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("/v1/question")
@Slf4j
public class AssessmentApi {

    @Autowired
    private ITitleBaseSrv titleBaseSrv;
    @Autowired
    private IMessageBundle messageBundle;

    @PostMapping("/first")
    public ResponseEntity<OutputAPI> getTitleBasic(){
        OutputAPI<ArrayList<TitleBasicsDto>> retVal ;
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/v1/question/first").toUriString());
        try{
           retVal = titleBaseSrv.getAllTitleBasics();
        }catch (Exception e){
            e.printStackTrace();
            retVal = null;
        }
        messageBundle.createMsg(retVal);
        return ResponseEntity.created(uri).body(retVal);
    }
}
