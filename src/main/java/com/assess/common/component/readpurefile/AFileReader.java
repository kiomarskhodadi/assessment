package com.assess.common.component.readpurefile;

import com.assess.common.component.FileDataStructure;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.stream.Stream;

@Slf4j
@Component
public abstract class AFileReader implements IFileReader{

    protected abstract void readRecordsFile(String line);
    public final FileDataStructure fileDataStructure;

    public AFileReader(FileDataStructure fileDataStructure) {
        this.fileDataStructure = fileDataStructure;
    }

    public void readFile(String fileName){
        long startTime = System.currentTimeMillis();
        File f = new File(getClass().getClassLoader().getResource(fileName).getPath());
        try (Stream<String> lines = Files.lines(f.toPath(), StandardCharsets.UTF_8)) {
            lines.forEach(line -> readRecordsFile(line));
        } catch (IOException e) {
            log.error("Error in read file " + fileName, e);
        }
        long endTime = System.currentTimeMillis();
        log.info("Read Data from  " + fileName + " : " + (endTime - startTime) + " Milli second");
        System.gc();
    }
}
