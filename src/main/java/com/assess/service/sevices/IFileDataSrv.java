package com.assess.service.sevices;

import com.assess.common.form.OutputAPIForm;
import com.assess.service.dto.TitleAttributeDto;

import java.util.List;
import java.util.Map;
import java.util.Vector;

public interface IFileDataSrv {
    void readAllFiles();
    OutputAPIForm<List<TitleAttributeDto>> searchFirstQuestion(Integer page, Integer pageSize);
    OutputAPIForm<List<TitleAttributeDto>> searchSecondQuestion(String firstActor,String secondActor,Integer page,Integer pageSize);
    OutputAPIForm<List<TitleAttributeDto>> searchThirdQuestion(String genre,Integer page,Integer pageSize);

}
