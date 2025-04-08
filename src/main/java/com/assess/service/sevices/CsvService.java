package com.assess.service.sevices;

import com.assess.service.dto.TitleBasicsDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
;

@Service
@Slf4j
public class CsvService {
    static Integer count = 0;
    private final Map<String, Object> csvDataCache = new ConcurrentHashMap<>();
    public void loadCsvFiles(String... filePaths) {
        Arrays.stream(filePaths).forEach(path -> csvDataCache.put(path, readCsvFileTitleBase(path)));
    }
    public Map<String, Map<String,ArrayList<TitleBasicsDto>>> readCsvFileTitleBase(String path){
        // rage of Ids  Genre
        Map<String, Map<String,ArrayList<TitleBasicsDto>>> retVal = new HashMap<>();
        try{
            Files.readAllLines(ResourceUtils.getFile("classpath:"+path).toPath())
                    .stream()
                    .map(line -> new TitleBasicsDto(line))
                    .forEach(dto -> {
                        if(StringUtils.hasLength(dto.getTconst())){
                            Map<String,ArrayList<TitleBasicsDto>> genreTitle;
                            if(retVal.containsKey(generateKey(dto.getTconst()))){
                                genreTitle = retVal.get(generateKey(dto.getTconst()));
                            }else{
                                genreTitle = new HashMap<>();
                            }
                            dto.getGenres().stream().forEach(genre->{
                                ArrayList<TitleBasicsDto> titles;
                                if(genreTitle.containsKey(genre)){
                                    titles = genreTitle.get(genre);
                                }else{
                                    titles = new ArrayList<>();
                                }
                                titles.add(dto);
                                genreTitle.put(genre,titles);
                            });
                            retVal.put(generateKey(dto.getTconst()),genreTitle);
                            count++;
                        }
                    });
        }catch (Exception e){
            log.error("Error in read Data",e);
        }
        return retVal;
    }
    public String generateKey(String key){
        String retVal ;
        Integer startRange =  Integer.parseInt(key.trim().substring(2)) - Integer.parseInt(key.trim().substring(key.trim().length()-7)) +1;
        retVal = startRange+"-"+(startRange+1000000);
        return retVal;
    }
}
