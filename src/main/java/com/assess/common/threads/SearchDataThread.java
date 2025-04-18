package com.assess.common.threads;

import com.assess.service.dto.TitleAttributeDto;
import com.assess.service.sevices.FileDataSrv;
import com.assess.common.component.FileDataStructure;

import java.util.Vector;

public class SearchDataThread implements Runnable{

    private FileDataStructure fileDataStructure;
    private final FileDataSrv fileDataSrv;
    private String genre;
    private Vector<TitleAttributeDto> titleAttributes;


    public SearchDataThread(FileDataStructure fileDataStructure,FileDataSrv fileDataSrv, String genre,Vector<TitleAttributeDto> titleAttributes) {
        this.fileDataStructure = fileDataStructure;
        this.fileDataSrv = fileDataSrv;
        this.genre = genre;
        this.titleAttributes = titleAttributes;
    }

    @Override
    public void run() {
        fileDataSrv.searchFirstQuestion(fileDataStructure.getTitleBasicsIndex().get(genre),fileDataStructure.getLineCache(),titleAttributes);
    }
}
