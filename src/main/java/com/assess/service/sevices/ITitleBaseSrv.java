package com.assess.service.sevices;

import com.assess.common.form.OutputAPI;
import com.assess.dao.entity.TitleBasics;
import com.assess.service.dto.TitleBasicsDto;

import java.util.ArrayList;
import java.util.List;

public interface ITitleBaseSrv {
    OutputAPI<ArrayList<TitleBasicsDto>> getAllTitleBasics();
}
