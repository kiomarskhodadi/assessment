package com.assess.filereader;


import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.stream.Stream;


/**
 * @Creator 4/16/2025
 * @Project IntelliJ IDEA
 * @Author k.khodadi
 **/


@Service
public class LargeFileReader {

    private final CSVIndexer csvIndexer;

    public LargeFileReader(CSVIndexer csvIndexer) {
        this.csvIndexer = csvIndexer;
    }

    public void readFile(String filePath) {

        File f = new File(filePath);
        long startTime = System.nanoTime();

        try (Stream<String> lines = Files.lines(f.toPath(), StandardCharsets.UTF_8).limit(15000000)) {
            lines.forEach(line -> {
                csvIndexer.indexLine(line);
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        long endTime = System.nanoTime();
        System.out.println("no buffer took " + (endTime - startTime) + " nanoseconds");



//        try (RandomAccessFile file = new RandomAccessFile(filePath, "r");
//            FileChannel channel = file.getChannel()) {
//            long fileSize = channel.size();
//            long position = 0;
//            long chunkSize = 1024 * 1024; // 1MB chunks
//
//            while (position < fileSize) {
//                long remaining = fileSize - position;
//                long currentChunk = Math.min(chunkSize, remaining);
//                MappedByteBuffer buffer = channel.map(
//                        FileChannel.MapMode.READ_ONLY,
//                        position,
//                        currentChunk
//                );
//                // Process buffer
//                processBuffer(buffer);
//                position += currentChunk;
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    private void processBuffer(MappedByteBuffer buffer) {String line = "";
        for (int i = 0; i < buffer.limit(); i++) {
            char a = (char) buffer.get();
            if (a == '\n') {
                csvIndexer.indexLine(buffer.toString());
                line = "";
            } else {
                line += Character.toString(a);
            }

        }
    }
}