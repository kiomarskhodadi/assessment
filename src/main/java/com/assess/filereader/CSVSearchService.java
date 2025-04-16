package com.assess.filereader;

import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

/**
 * @Creator 4/16/2025
 * @Project IntelliJ IDEA
 * @Author k.khodadi
 **/




@Service
public class CSVSearchService {
    private final ResourceLoader resourceLoader;
    private final CSVIndexer indexer;
    private final LargeFileReader fileReader;

    public CSVSearchService(ResourceLoader resourceLoader, CSVIndexer indexer, LargeFileReader fileReader) {
        this.resourceLoader = resourceLoader;
        this.indexer = indexer;
        this.fileReader = fileReader;
    }

    public void loadCSV(String filename) throws IOException {
        fileReader.readFile(filename);
    }

    public List<String> search(String term) {
        return indexer.search(term);
    }
}
