package com.assess.controller;

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
import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("/v1/question")
@Slf4j
public class AssessmentApi {

    @Autowired
    private ITitleBaseSrv titleBaseSrv;

    @PostMapping("/first")
    public ResponseEntity<List<TitleBasicsDto>> getTitleBasic(){
        List<TitleBasicsDto> retVal ;
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/user/ordinary").toUriString());
        try{
           retVal = titleBaseSrv.getAllTitleBasics();
        }catch (Exception e){
            e.printStackTrace();
            retVal = null;
        }
        return ResponseEntity.created(uri).body(retVal);
    }
}
