package com.practice.util;

import com.practice.exception.FileException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * @author haoyue
 */
public class FileUtil {

    /**
     * read input data file to string list
     * @param inputDataFile absolute file path
     * @return
     */
    public static List<String> readInputDataFile(String inputDataFile){

        File file = Paths.get(inputDataFile).toFile();
        if (file.isDirectory()){
            throw new FileException(inputDataFile + " is not a file.");
        }

        List<String> inputLines = new ArrayList<>();

        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String lineTxt;
            while ((lineTxt = br.readLine()) != null) {
                inputLines.add(lineTxt);
            }
            br.close();
        } catch (Exception e) {
            throw new FileException("read " +inputDataFile + " error. message=" + e.getMessage());
        }

        return inputLines;
    }
}
