package com.assess.service.sevices;

import com.assess.common.form.OutputAPIForm;
import com.assess.service.dto.TitleBasicsDto;

import java.util.ArrayList;

public interface ITitleBaseSrv {
    OutputAPIForm<ArrayList<TitleBasicsDto>> getAllTitleBasics();
    OutputAPIForm<ArrayList<TitleBasicsDto>> getAllTitleBasics(String actorFirst, String actorSecond);
    OutputAPIForm<ArrayList<TitleBasicsDto>> getAllTitleBasics(String genre);
}
