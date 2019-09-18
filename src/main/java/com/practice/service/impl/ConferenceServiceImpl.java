package com.practice.service.impl;

import com.practice.model.Conference;
import com.practice.service.ConferenceService;
import com.practice.service.InputService;
import com.practice.service.PrintService;
import com.practice.service.TackService;

import java.util.List;

public class ConferenceServiceImpl implements ConferenceService {


    private TackService trackService;

    private InputService inputService;

    private PrintService printService;

    public ConferenceServiceImpl() {
        this.trackService = new TrackServiceImpl();
        this.inputService = new InputServiceImpl();
        this.printService = new PrintServiceImpl();
    }

    @Override
    public void TrackSession(String fileName) {

        List<Conference> sequentialConferences = inputService.getSequentialConferences(fileName);
        List<List<Conference>> sessionDays = trackService.getSessionDays(sequentialConferences);
        printService.printSessionDay(sessionDays);
    }
}
