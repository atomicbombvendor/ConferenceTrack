package com.practice.service.impl;


import com.practice.common.ConferenceTakeTimesEnum;
import com.practice.comparator.ConferenceComparator;
import com.practice.model.Conference;
import com.practice.service.ConferenceTackService;
import com.practice.util.ConferenceUtil;
import com.practice.util.RegexUtil;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ConferenceTaskServiceImpl implements ConferenceTackService {

    @Override
    public List<Conference> getSequentialConferences(List<String> inputLines) {

        List<Conference> conferences = new ArrayList<>();
        for (String line : inputLines) {
            conferences.add(RegexUtil.getConference(line));
        }

        setValue(conferences);
        conferences.sort(new ConferenceComparator());
        return conferences;
    }

    @Override
    public List<List<Conference>> getSessionDays(List<Conference> sequentialConferences) {

        List<Conference> conferences = sequentialConferences;

//        if (ifAllTakeTimeIsMoreTwoDays(conferences)) {
//            throw new InputIllegalException("Total minutes of conference is more than tow days");
//        }

        int order = 10;

        List<List<Conference>> sessions = new LinkedList<>();

        while (conferences.size() > 0) {

            int numberOfConference = conferences.size();
            InputParameter input = new InputParameter(order);
            int[][] table = ConferenceUtil.init(numberOfConference, input.getTotalMinutes());

            List<Conference> session = new LinkedList<>();
            ConferenceUtil.findScheduled(table, input.getTotalMinutes(), numberOfConference, conferences);
            ConferenceUtil.findTrack(table, numberOfConference, input.getTotalMinutes(), conferences, order, session);
            ConferenceUtil.removePlanedConference(conferences);
            ConferenceUtil.setScheduleTime(session, input.isIfMorning());
            sessions.add(session);
            order += 10;
        }
        return sessions;
    }

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

    /**
     * only two day.
     *
     * @param conferences
     * @return
     */
    private boolean ifAllTakeTimeIsMoreTwoDays(List<Conference> conferences) {

        Integer allTimeTakes = 0;

        for (Conference c : conferences) {
            allTimeTakes += c.getTakeTime();
        }
        return (allTimeTakes / ConferenceTakeTimesEnum.ALL_DAY_MAX.getValue()) > 2;
    }

    private void printSession(int trackCount, List<List<Conference>> sessionDays){

        if (trackCount < sessionDays.size()) {
            for (Conference s : sessionDays.get(trackCount)) {
                System.out.println(s.printTrack());
            }
        }
    }

    private static class InputParameter {

        private int totalMinutes;

        private boolean ifMorning;

        private int order;

        InputParameter(int order) {
            Integer morningTotalMinutes = ConferenceTakeTimesEnum.MORNING_SESSION.getValue();
            Integer afternoonTotalMinutes = ConferenceTakeTimesEnum.AFTER_MAX_SESSION.getValue();

            if ((order / 10) % 2 == 0) {
                this.totalMinutes = afternoonTotalMinutes;
                this.ifMorning = false;
            } else {
                this.totalMinutes = morningTotalMinutes;
                this.ifMorning = true;
            }
        }

        int getTotalMinutes() {
            return totalMinutes;
        }

        public void setTotalMinutes(int totalMinutes) {
            this.totalMinutes = totalMinutes;
        }

        boolean isIfMorning() {
            return ifMorning;
        }

        public void setIfMorning(boolean ifMorning) {
            this.ifMorning = ifMorning;
        }

        public int getOrder() {
            return order;
        }

        public void setOrder(int order) {
            this.order = order;
        }
    }
}
