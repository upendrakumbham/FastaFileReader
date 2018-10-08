package com.upendra.fastafilereader;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * Created by ukumbham on 30/09/2018.
 */
public class FastaFileProcessor implements Callable<FileOutput> {

    private String filePath;
    private int inputFileIndex;

    public FastaFileProcessor(String filePath, int inputFileIndex) {
        this.filePath = filePath;
        this.inputFileIndex = inputFileIndex;
    }

    /**
     * Computes a result, or throws an exception if unable to do so.
     *
     * @return computed result
     * @throws Exception if unable to compute a result
     */
    @Override
    public FileOutput call() throws Exception {

        // Validate file path

        Boolean isValidFilePath = isValidFilePath(this.filePath);

        if(!isValidFilePath){
            return null;
        }

        // read lines from the gzip file
        List<String> lines = GZIPUtils.readCompressedGzipFile(this.filePath);
        // process the lines
        List<String> sequences = getSequences(lines);
        FileOutput output = new FileOutput(sequences, this.inputFileIndex);
        return output;
    }

    private List<String> getSequences(List<String> inputLines) {
        List<String> sequences = new ArrayList<>();
        StringBuilder stringBuilder = new StringBuilder();
        // iterate through lines
        Boolean hasSequenceStarted = false;
        for (String line : inputLines) {
            if (line.startsWith(">")) {
                if (hasSequenceStarted && stringBuilder != null) {
                    // sequence has started
                    // sequence lines are added to string builder
                    // get the string from builder and add to sequences
                    sequences.add(stringBuilder.toString());
                }
                hasSequenceStarted = true;
                stringBuilder = new StringBuilder();
                continue;
            }
            if (hasSequenceStarted && stringBuilder != null) {
                stringBuilder.append(line);
            }
        }
        // Completed iterating through all the lines
        // if the sequence has started
        // and the builder is not null add the contents to sequences
        if (hasSequenceStarted && stringBuilder != null) {

            // completed iterating through the loop
            // lines are appended to string builder
            // get the string from builder and add to lines
            sequences.add(stringBuilder.toString());
        }

        return sequences;
    }

    private Boolean isValidFilePath(String filePath){
        File f = new File(filePath);
        if(f.exists() && !f.isDirectory()) {
            return true;
        }
        return false;
    }
}
