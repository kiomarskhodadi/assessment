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
public class FileReaderTitlePrincipals   extends AFileReader{
    public FileReaderTitlePrincipals(FileDataStructure fileDataStructure) {
        super(fileDataStructure);
    }

    @Override
    public void readRecordsFile(String line) {
        String[] columns = line.split("\t");
        TitleAttributeDto titleAttribute = fileDataStructure.getLineCache().get(GeneralUtility.convertToId(columns[0]));
        if(Objects.nonNull(titleAttribute)){
            if(!columns[1].equals("\\N")){
                Arrays.stream(columns[1].split(",")).forEach(nConst -> {
                    if(Objects.nonNull(fileDataStructure.getBaseJob().get(columns[3]))){
                        List<Integer> nConsts = titleAttribute.getPrinciples().get(fileDataStructure.getBaseJob().get(columns[3]));
                        if(Objects.isNull(nConsts)){
                            nConsts = new ArrayList<>();
                        }
                        nConsts.add(GeneralUtility.convertToId(columns[2]));
                        titleAttribute.getPrinciples().put(fileDataStructure.getBaseJob().get(columns[3]),nConsts);
                    }
                });
            }
        }
    }
}
