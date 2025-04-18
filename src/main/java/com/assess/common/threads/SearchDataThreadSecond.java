package com.assess.common.threads;

import com.assess.common.component.FileDataStructure;
import com.assess.service.dto.TitleAttributeDto;
import com.assess.service.sevices.FileDataSrv;

import java.util.Vector;

public class SearchDataThreadSecond implements Runnable{

    private final FileDataStructure fileDataStructure;
    private final FileDataSrv fileDataSrv;
    private String genre;
    private String firstActor;
    private String secondActor;
    private Vector<TitleAttributeDto> titleAttributes;




    public SearchDataThreadSecond(FileDataStructure fileDataStructure,
                                  FileDataSrv fileDataSrv,
                                  String firstActor,
                                  String secondActor,
                                  String genre,
                                  Vector<TitleAttributeDto> titleAttributes) {
        this.fileDataStructure = fileDataStructure;
        this.fileDataSrv = fileDataSrv;
        this.genre = genre;
        this.titleAttributes = titleAttributes;
        this.firstActor =firstActor;
        this.secondActor = secondActor;
    }


    @Override
    public void run() {
        fileDataSrv.searchSecondQuestion(fileDataStructure.getTitleBasicsIndex().get(genre),fileDataStructure.getLineCache(),firstActor,secondActor,titleAttributes);
    }
}
