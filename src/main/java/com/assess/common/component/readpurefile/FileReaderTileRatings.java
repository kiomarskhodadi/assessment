package com.assess.common.component.readpurefile;

import com.assess.common.utilities.GeneralUtility;
import com.assess.service.dto.TitleAttributeDto;
import com.assess.common.component.FileDataStructure;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class FileReaderTileRatings extends AFileReader {

    public FileReaderTileRatings(FileDataStructure fileDataStructure) {
        super(fileDataStructure);
    }

    @Override
    public void readRecordsFile(String line) {
        String[] columns = line.split("\t");
        TitleAttributeDto titleAttribute = fileDataStructure.getLineCache().get(GeneralUtility.convertToId(columns[0]));
        if(Objects.nonNull(titleAttribute)){
            if(!columns[1].equals("\\N")){
                titleAttribute.setAverageRating(GeneralUtility.convertToFloat(columns[1]));
            }
            if(!columns[2].equals("\\N")){
                titleAttribute.setNumVotes(GeneralUtility.convertToInteger(columns[2]));
            }
        }
    }
}
