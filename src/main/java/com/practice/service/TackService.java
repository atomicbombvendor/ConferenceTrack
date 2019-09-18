package com.practice.service;

import com.practice.model.Conference;

import java.util.List;

public interface TackService {

    /**
     * get plan of conference every day
     * @param sequentialConferences conferences
     * @return
     */
    List<List<Conference>> getSessionDays(List<Conference> sequentialConferences);

}
