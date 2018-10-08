package com.upendra.fastafilereader;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * Class holds methods to read a gzip file and creating gzip files.
 * Created by ukumbham on 30/09/2018.
 */
public class GZIPUtils {

    /**
     * Reads compressed gzip file and returns the file contents as string array.
     * Each line item in the file will be returned as an item in the array.
     * @param gzipFile
     * @return
     * @throws FileNotFoundException
     */
    public static List<String> readCompressedGzipFile(String gzipFile) throws FileNotFoundException {
        System.out.println("Processing " + gzipFile);
        List<String> fileContents = new ArrayList<String>();
        File fileTobeRead = new File(gzipFile);

        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(
                    new InputStreamReader(
                            new GZIPInputStream(
                                    new FileInputStream(fileTobeRead)
                            )
                    )
            );

            String line;

            while ((line = bufferedReader.readLine()) != null) {
                fileContents.add(line);
            }
        } catch (IOException e) {
            //e.printStackTrace();
        }

        return fileContents;
    }

    /**
     * Compresses the file contents and returns a gzip file.
     * @param fileContents
     * @param gzipFile
     */
    public static void compressGzipFile(byte[] fileContents,
                                        String gzipFile) {
        try {

            FileOutputStream fos = new FileOutputStream(gzipFile);
            GZIPOutputStream gzipOS = new GZIPOutputStream(fos);

            if (fileContents != null && fileContents.length > 0) {
                gzipOS.write(fileContents, 0, fileContents.length);
            }

            gzipOS.close();
            fos.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
