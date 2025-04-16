package com.assess.filereader;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Calendar;
import java.util.List;

/**
 * @Creator 4/16/2025
 * @Project IntelliJ IDEA
 * @Author k.khodadi
 **/



@RestController
@RequestMapping("/api/csv")
public class CSVController {
    private final CSVSearchService searchService;
    private final LargeFileReader largeFileReader;



    public CSVController(CSVSearchService searchService, LargeFileReader largeFileReader) {
        this.searchService = searchService;
        this.largeFileReader = largeFileReader;
    }

    @GetMapping("/search")
    public ResponseEntity<List<String>> search(@RequestParam String term) {
        return ResponseEntity.ok(searchService.search(term));
    }

    @GetMapping("/load")
    public void load(@RequestParam String path) {
        try{
            System.out.println(Calendar.getInstance().getTime());
            largeFileReader.readFile(getClass().getClassLoader().getResource(path).getPath());
            System.out.println(Calendar.getInstance().getTime());
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
