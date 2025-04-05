package com.assess.service.sevices;

import com.assess.dao.entity.TitleBasics;
import com.assess.dao.repository.ITitleBaseRepo;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TitleBaseSrv implements ITitleBaseSrv{
    private final ITitleBaseRepo titleBaseRepo;

    public TitleBaseSrv(ITitleBaseRepo titleBaseRepo) {
        this.titleBaseRepo = titleBaseRepo;
    }

    public List<TitleBasics> getAllTitleBasics(){
        List<TitleBasics> retVal = new ArrayList<>();
        retVal = titleBaseRepo.getTitleBaseQuestionFirst(  PageRequest.of(0, 10, Sort.by("tconst")));
        return retVal;
    }
}
