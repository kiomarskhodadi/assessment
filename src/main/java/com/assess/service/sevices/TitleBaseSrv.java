package com.assess.service.sevices;

import com.assess.common.exception.BusinessCodeException;
import com.assess.common.form.OutputAPI;
import com.assess.dao.entity.TitleBasics;
import com.assess.dao.repository.ITitleBaseRepo;
import com.assess.service.dto.TitleBasicsDto;
import org.aspectj.apache.bcel.classfile.CodeException;
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

    public OutputAPI<ArrayList<TitleBasicsDto>> getAllTitleBasics(){
        OutputAPI<ArrayList<TitleBasicsDto>> retVal = new OutputAPI<>();
        try{
            List<TitleBasics> data = titleBaseRepo.getTitleBaseQuestionFirst(  PageRequest.of(0, 10+1, Sort.by("tconst")));
            if(data != null && data.size() > 10){
                retVal.setNextPage(true);
                data.remove(10+1);
            }
            retVal.setData(data.stream().map(ent -> new TitleBasicsDto(ent)).collect(Collectors.toCollection(ArrayList::new)));
        }catch (Exception e){
            retVal.setSuccess(false);
            retVal.getErrors().add(BusinessCodeException.DATABASE_EXCEPTION);
        }
        return retVal;
    }
}
