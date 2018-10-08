package com.upendra.fastafilereader;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * Created by ukumbham on 30/09/2018.
 */
public class FastaFileReader {

    public static void main(String[] args) {

        if (args != null) {
            System.out.println("input args length: " + args.length);
            // Input args file names and the number of worker threads
            // File names will be at index 0
            // Number of worker threads at index 1
            if (args.length == 2) {
                try {
                    String[] inputFileNames = args[0].split(",");
                    int numberOfWorkerThreads = Helpers.parseIntWithDefault(args[1],1);
                    if(inputFileNames.length>0) {
                        // Start processing only if there are any input params are provided
                        List<FileOutput> results = invokeTheExecutor(inputFileNames,
                                numberOfWorkerThreads);
                        generateOutput(results);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static List<FileOutput> invokeTheExecutor(String[] fileNames,
                                                      int numberOfThreads)
            throws InterruptedException, ExecutionException {
        List<FileOutput> fileOutput = new ArrayList<>();

        ExecutorService executorService = Executors.newFixedThreadPool(numberOfThreads);
        List<Callable<FileOutput>> fileProcessors = new ArrayList<>();

        int inputFileIndex = 0;

        for (String fileName : fileNames) {
            FastaFileProcessor fileProcessor = new FastaFileProcessor(fileName, inputFileIndex);
            fileProcessors.add(fileProcessor);
            inputFileIndex++;
        }

        List<Future<FileOutput>> results = executorService.invokeAll(fileProcessors);
        executorService.shutdown();

        for (Future<FileOutput> resultFuture : results) {
            FileOutput result = resultFuture.get();
            if (result != null && result.getSequences() != null && result.getSequences().size()>0) {
                // only add the file if there are sequences in the input file.
                fileOutput.add(result);
            }
        }

        return fileOutput;
    }

    private static void generateOutput(List<FileOutput> results) {
        ReportGenerator reportGenerator = new ReportGenerator(results);
        reportGenerator.generateReport();
        SequenceFileWriter sequenceFileWriter = new SequenceFileWriter(results);
        sequenceFileWriter.generateOutput();
    }
}
