package com.practice.util;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Test;

import java.util.Iterator;
import java.util.List;


public class FileUtilTest {

    @Test
    public void readInputDataFile() {

        String inputFile = this.getClass().getResource("/") + "Conferences.txt";

        inputFile = inputFile.replace("file:/", "");
        List<String> result = FileUtil.readInputDataFile(inputFile);
        Assert.assertEquals(19, result.size());
    }

    @Test
    public void testIterator(){
        String inputFile = this.getClass().getResource("/") + "Conferences.txt";

        inputFile = inputFile.replace("file:/", "");
        List<String> result = FileUtil.readInputDataFile(inputFile);

        Iterator<String> iterator = result.iterator();
        while (iterator.hasNext()){
            String tmp = iterator.next();
            iterator.remove();
        }
        Iterator iterator2 = result.listIterator();


    }
}