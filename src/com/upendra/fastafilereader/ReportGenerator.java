package com.upendra.fastafilereader;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ukumbham on 30/09/2018.
 */
public class ReportGenerator {

    //private Map<Character, Integer> sequenceBase;
    private final String fileCnt = "FILE_CNT";
    private final String sequenceCnt = "SEQUENCE_CNT";
    private final String baseCnt = "BASE_CNT";
    private List<FileOutput> results;

//    public ReportGenerator(int fileCount, int sequenceCount,
//                           Map<Character, Integer> sequenceBase) {
//        this.sequenceBase = sequenceBase;
//    }

    public ReportGenerator(List<FileOutput> results) {
        this.results = results;
    }

    public void generateReport() {
        Map<Character, Integer> sequenceBase = getBaseCharCount(this.results);
        int baseCount = getSum(sequenceBase);
        int sequenceCount = getSequenceCount(this.results);
        try (Writer writer = new BufferedWriter(
                new OutputStreamWriter(
                        new FileOutputStream("REPORT.txt"),
                        StandardCharsets.UTF_8))) {
            writer.write(getFormattedLine(fileCnt, results.size()));
            writer.write(getFormattedLine(sequenceCnt, sequenceCount));
            writer.write(getFormattedLine(baseCnt, baseCount));

            for (Map.Entry<Character, Integer> entry :
                    sequenceBase.entrySet()) {
                Character key = entry.getKey();
                Integer value = entry.getValue();
                writer.write(getFormattedLine(String.valueOf(key), value));
            }
        } catch (IOException ex) {
            // Handle me
            String message = ex.getMessage();
        }
    }

    private int getSequenceCount(List<FileOutput> results) {
        // each file output contains sequences
        // get sequence count from each file and then sum all of them to get the final count
        int sequenceCount = 0;

        for (FileOutput result:results){
            sequenceCount = sequenceCount + result.getSequences().size();
        }
        return sequenceCount;
    }

    private String getFormattedLine(String key, int value) {
        return key + "\t" + value + System.lineSeparator();
    }

    private static Integer getSum(final Map<Character, Integer> data) {
        return data.values().stream().mapToInt(Number::intValue).sum();
    }

    private static Map<Character, Integer> getBaseCharCount(List<FileOutput> results) {
        Map<Character, Integer> charMapCount = new HashMap<>();

        for (FileOutput output : results) {
            Map<Character, Integer> fileSequenceCount = output.getCharCount();
            for (Character key : fileSequenceCount.keySet()) {

                if (charMapCount.containsKey(key)) {
                    int charCount = charMapCount.get(key);
                    charCount++;
                    charMapCount.put(key, charCount + fileSequenceCount.get(key));
                } else {
                    charMapCount.put(key, fileSequenceCount.get(key));
                }
            }
        }
        return charMapCount;

    }
}
