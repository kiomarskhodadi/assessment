package com.assess.common.component;

import com.assess.service.dto.TitleAttributeDto;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Setter
@Getter
public class FileDataStructure {

    private Map<Integer,String> baseGenres = new HashMap<>();
    private Map<String,Integer> baseJob = new HashMap<> (Map.of("director",1,"writer",2,"actor",3));
    private Integer indexJob = 3;
    //              genres,year
    private Map<String,Map<Integer, List<Integer>>> titleBasicsIndex = new HashMap<>();
    private Map<Integer, TitleAttributeDto> lineCache = new HashMap<>();
    private Map<Integer, Integer> nameBasicCache = new HashMap<>();

    public void clear(){
        baseGenres = new HashMap<>();
        titleBasicsIndex = new HashMap<>();
        lineCache = new HashMap<>();
        nameBasicCache = new HashMap<>();
    }

}
