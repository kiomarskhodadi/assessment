package com.assess.common.component.readpurefile;

import com.assess.common.utilities.GeneralUtility;
import com.assess.service.dto.TitleAttributeDto;
import com.assess.common.component.FileDataStructure;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Component
public class FileReaderTitleCrew  extends AFileReader{

    public FileReaderTitleCrew(FileDataStructure fileDataStructure) {
        super(fileDataStructure);
    }
    @Override
    public void readRecordsFile(String line) {
        String[] columns = line.split("\t");
        TitleAttributeDto titleAttribute = fileDataStructure.getLineCache().get(columns[0]);
        if(Objects.nonNull(titleAttribute)){
            if(!columns[1].equals("\\N")){
                Arrays.stream(columns[1].split(",")).forEach(nConst -> {
                    List<Integer> nConsts = fileDataStructure.getLineCache().get(columns[0]).getPrinciples().get(fileDataStructure.getBaseJob().get("director"));
                    if(Objects.isNull(nConsts)){
                        nConsts = new ArrayList<>();
                    }
                    nConsts.add(GeneralUtility.convertToId(nConst));
                    fileDataStructure.getLineCache().get(columns[0]).getPrinciples().put(fileDataStructure.getBaseJob().get("director"),nConsts);
                });
            }
            if(!columns[2].equals("\\N")){
                Arrays.stream(columns[2].split(",")).forEach(nConst -> {
                    List<Integer> nConsts = fileDataStructure.getLineCache().get(columns[0]).getPrinciples().get(fileDataStructure.getBaseJob().get("write"));
                    if(Objects.isNull(nConsts)){
                        nConsts = new ArrayList<>();
                    }
                    nConsts.add(GeneralUtility.convertToId(nConst));
                    fileDataStructure.getLineCache().get(columns[0]).getPrinciples().put(fileDataStructure.getBaseJob().get("write"),nConsts);
                });
            }
        }
    }
}
