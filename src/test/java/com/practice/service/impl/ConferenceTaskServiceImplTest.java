package com.practice.service.impl;

import com.practice.model.Conference;
import com.practice.service.ConferenceTackService;
import com.practice.util.FileUtil;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class ConferenceTaskServiceImplTest {

    private ConferenceTackService service = new ConferenceTaskServiceImpl();

    @Test
    public void getSequentialConferences() {

        List<Conference> result = service.getSequentialConferences(getInputLines());
        Assert.assertEquals(result.size(), 19);
    }

    @Test
    public List<List<Conference>> getSessionDays() {

        List<Conference> result = service.getSequentialConferences(getInputLines());
        return service.getSessionDays(result);

    }

    private List<String> getInputLines(){

        String inputFile = this.getClass().getResource("/") + "Conferences.txt";

        inputFile = inputFile.replace("file:/", "");
        List<String> result = FileUtil.readInputDataFile(inputFile);
        return result;
    }
}