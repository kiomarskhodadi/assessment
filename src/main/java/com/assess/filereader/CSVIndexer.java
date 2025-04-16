package com.assess.filereader;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Creator 4/16/2025
 * @Project IntelliJ IDEA
 * @Author k.khodadi
 **/


@Service
public class CSVIndexer {
    private Map<String, List<Long>> columnIndex = new HashMap<>();
    private Map<Long, String> lineCache = new HashMap<>();
    private long currentOffset = 0;

    public void indexLine(String line) {
        String[] columns = line.split("\t");
        String keyColumn = columns[0]; // Assuming first column is search key

        // Index the position
        if (!columnIndex.containsKey(keyColumn)) {
            columnIndex.put(keyColumn, new ArrayList<>());
        }
        columnIndex.get(keyColumn).add(currentOffset);

        // Cache the line (optional, memory permitting)
        lineCache.put(currentOffset, line);

        currentOffset += line.length() + 1; // +1 for newline
    }

    public List<String> search(String key) {
        List<String> results = new ArrayList<>();
        if (columnIndex.containsKey(key)) {
            for (Long offset : columnIndex.get(key)) {
                results.add(lineCache.get(offset));
            }
        }
        return results;
    }
}
