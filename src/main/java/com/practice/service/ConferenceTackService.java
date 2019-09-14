package com.practice.service;

import com.practice.model.Conference;

import java.util.List;

public interface ConferenceTackService {

    /**
     * process input line, transform to entity and get sequential conferences
     * @param inputLines input data
     * @return
     */
    List<Conference> getSequentialConferences(List<String> inputLines);

    /**
     * get plan of conference every day
     * @param sequentialConferences conferences
     * @return
     */
    List<List<Conference>> getSessionDays(List<Conference> sequentialConferences);
}
