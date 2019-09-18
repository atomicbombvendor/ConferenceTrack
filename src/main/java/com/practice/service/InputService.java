package com.practice.service;

import com.practice.model.Conference;

import java.util.List;

public interface InputService {

    /**
     * process input line, transform to entity and get sequential conferences
     * @return
     */
    List<Conference> getSequentialConferences(String fileName);

}
