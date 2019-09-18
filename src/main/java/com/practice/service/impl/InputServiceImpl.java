package com.practice.service.impl;

import com.practice.model.Conference;
import com.practice.service.InputService;
import com.practice.util.FileUtil;
import com.practice.util.RegexUtil;

import java.util.ArrayList;
import java.util.List;

public class InputServiceImpl implements InputService {

    @Override
    public List<Conference> getSequentialConferences(String fileName) {

        String inputFile = this.getClass().getResource("/") + fileName;
        inputFile = inputFile.replace("file:/", "");
        List<String> inputLines = FileUtil.readInputDataFile(inputFile);

        List<Conference> conferences = new ArrayList<>();
        for (String line : inputLines) {
            conferences.add(RegexUtil.getConference(line));
        }

        setValue(conferences);
        return conferences;
    }

    /**
     * priority get from take times.
     * Longer take time with bigger value of priority;
     *
     * @param conferences
     * @return
     */
    private void setValue(List<Conference> conferences) {

        for (Conference tmp : conferences) {
            tmp.setValue((tmp.getTakeTime() / 5));
        }
    }
}
