package com.practice.service.impl;

import com.practice.model.Conference;
import com.practice.service.PrintService;

import java.util.List;

public class PrintServiceImpl implements PrintService {

    @Override
    public void printSessionDay(List<List<Conference>> sessionDays) {

        int trackCount = 1;
        for (int i=0; i < sessionDays.size(); i+=2){
            System.out.println("Track " + trackCount + ":");

            printSession(i, sessionDays);
            printSession((i + 1), sessionDays);
            System.out.println();
            trackCount++;
        }
    }

    private void printSession(int trackCount, List<List<Conference>> sessionDays){

        if (trackCount < sessionDays.size()) {
            for (Conference s : sessionDays.get(trackCount)) {
                System.out.println(s.printTrack());
            }
        }
    }
}
