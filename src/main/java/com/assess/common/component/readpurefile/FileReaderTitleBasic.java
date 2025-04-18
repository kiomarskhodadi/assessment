package com.assess.common.component.readpurefile;

import com.assess.common.utilities.GeneralUtility;
import com.assess.service.dto.TitleAttributeDto;
import com.assess.common.component.FileDataStructure;
import org.springframework.stereotype.Component;
import java.util.*;

@Component
public class FileReaderTitleBasic extends AFileReader {

    public FileReaderTitleBasic(FileDataStructure fileDataStructure) {
        super(fileDataStructure);
    }

    @Override
    public void readRecordsFile(String line) {
        String[] columns = line.split("\t");
        Map<Integer, List<Integer>> yearsTitle;
        List<Integer> genreYearTitles;
        String[] genres = columns[8].split(",");
        Integer year = GeneralUtility.convertToInteger(columns[5]);
        Integer title = GeneralUtility.convertToId(columns[0]);
        if(Objects.nonNull(title)){
            for(String genre:genres){
                yearsTitle = fileDataStructure.getTitleBasicsIndex().get(genre);
                if(Objects.isNull(yearsTitle)){
                    yearsTitle = new HashMap<>();
                }
                genreYearTitles = yearsTitle.get(year);
                if(Objects.isNull(genreYearTitles)){
                    genreYearTitles = new ArrayList<>();
                }
                genreYearTitles.add(title);
                yearsTitle.put(year,genreYearTitles);
                fileDataStructure.getTitleBasicsIndex().put(genre,yearsTitle);
            }
            fileDataStructure.getLineCache().put(title, new TitleAttributeDto(title));
        }

    }
}
