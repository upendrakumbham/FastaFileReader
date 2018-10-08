package com.upendra.fastafilereader;

import java.io.*;
import java.util.*;

/**
 * Class holds the methods to generate the sequence file output.
 * Created by ukumbham on 06/10/2018.
 */
public class SequenceFileWriter {

    private List<FileOutput> results;
    String gzipFileName = "SEQUENCE.FASTA.GZ";

    public SequenceFileWriter(List<FileOutput> results){
        this.results = results;
    }

    public void generateOutput(){
        List<String> outputSequences = getSequences();
        byte[] fileContents = getSequenceFileContent(outputSequences);
        gZipSequenceFile(fileContents);
    }

    private void gZipSequenceFile(byte[] fileContents) {
        GZIPUtils.compressGzipFile(fileContents, gzipFileName);
    }

    private List<String> getSequences() {
        // iterate through the in out filenames
        // for each file corresponding sequences are available in results variable

        // validate the input file results array
        if(results == null || results.size() == 0)
            return new ArrayList<>();

        FileOutput maxSequenceFile = this.results
                .stream()
                .max(Comparator.comparing(FileOutput::getSequenceCount))
                .orElseThrow(NoSuchElementException::new);

        sortFileResultsByInputFileIndex();

        List<String> outputSequences = new ArrayList<String>();

        for (int sequenceIndex=0; sequenceIndex<maxSequenceFile.getSequenceCount();sequenceIndex++){
            StringBuilder sequenceBuilder = new StringBuilder();
            for (FileOutput result : this.results){
                if(result.getSequences().size() > sequenceIndex){
                    sequenceBuilder.append(result.getSequences().get(sequenceIndex));
                }
            }
            outputSequences.add(sequenceBuilder.toString());
        }

        return outputSequences;
    }

    private void sortFileResultsByInputFileIndex() {
        // Sort the file outputs by the input file index
        Collections.sort(this.results, new Comparator<FileOutput>(){
            public int compare(FileOutput firstFileOutput, FileOutput secondFileOutput){
                if(firstFileOutput.getInputFileIndex() == secondFileOutput.getInputFileIndex())
                    return 0;
                return firstFileOutput.getInputFileIndex() < secondFileOutput.getInputFileIndex() ? -1 : 1;
            }
        });
    }

    private byte[] getSequenceFileContent(List<String> inputSequences){
        BufferedWriter writer = null;
        ByteArrayOutputStream outputStream = null;
        try {
            outputStream = new ByteArrayOutputStream();
            writer = new BufferedWriter(new OutputStreamWriter(outputStream));

            int sequenceIndex = 1;

            for (String sequence: inputSequences){
                writer.write(">"+sequenceIndex);
                writer.newLine();
                writer.write(sequence);
                writer.newLine();
                sequenceIndex++;
            }
        } catch (IOException ex) {
            // Report
        } finally {
            try {writer.close();} catch (Exception ex) {/*ignore*/}
        }
        return outputStream.toByteArray();
    }
}
