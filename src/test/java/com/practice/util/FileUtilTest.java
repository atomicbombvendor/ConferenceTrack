package com.practice.util;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;


public class FileUtilTest {

    @Test
    public void readInputDataFile() {

        String inputFile = this.getClass().getResource("/") + "Conferences.txt";

        inputFile = inputFile.replace("file:/", "");
        List<String> result = FileUtil.readInputDataFile(inputFile);
        Assert.assertEquals(19, result.size());
    }
}