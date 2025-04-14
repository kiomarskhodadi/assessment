package com.assess.service.sevices;

import com.assess.common.form.OutputAPIForm;
import com.assess.service.dto.TitleBasicsDto;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

public interface ITitleBaseSrv {
    OutputAPIForm<ArrayList<TitleBasicsDto>> getAllTitleBasics(Integer page,Integer pageSize);
    OutputAPIForm<ArrayList<TitleBasicsDto>> getAllTitleBasics(String actorFirst, String actorSecond,Integer page,Integer pageSize);
    OutputAPIForm<ArrayList<TitleBasicsDto>> getAllTitleBasics(String genre,Integer page,Integer pageSize);
}
