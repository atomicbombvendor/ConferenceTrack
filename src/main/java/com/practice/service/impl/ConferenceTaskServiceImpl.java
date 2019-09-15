package com.practice.service.impl;


import com.practice.common.ConferenceTakeTimesEnum;
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
//        conferences.sort(new ConferenceComparator());
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
            InputParameter input = new InputParameter(order, numberOfConference);

            List<Conference> session = new LinkedList<>();
            ConferenceUtil.findScheduled(input.getTable(), input.getTotalMinutes(), numberOfConference, conferences);
            ConferenceUtil.findTrack(input.getTable(), numberOfConference, input.getTotalMinutes(), conferences, order, session);
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

        private int[][] table;

        InputParameter(int order, int numberOfConference) {
            Integer morningTotalMinutes = ConferenceTakeTimesEnum.MORNING_SESSION.getValue();
            Integer afternoonTotalMinutes = ConferenceTakeTimesEnum.AFTER_MAX_SESSION.getValue();

            if ((order / 10) % 2 == 0) {
                this.totalMinutes = afternoonTotalMinutes;
                this.ifMorning = false;
            } else {
                this.totalMinutes = morningTotalMinutes;
                this.ifMorning = true;
            }

            this.table = initTable(numberOfConference, totalMinutes);
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

        public int[][] getTable() {
            return table;
        }

        public void setTable(int[][] table) {
            this.table = table;
        }

        public void setOrder(int order) {
            this.order = order;
        }


        private int[][] initTable(Integer conferenceSize, Integer space){

            int[][] table = new int[conferenceSize + 1][space + 1];
            for (int i=0; i <= table.length-1; i++){
                table[i][0] = 0;
            }

            for (int i=0; i <= table[0].length-1; i++){
                table[0][i] = 0;
            }
            return table;
        }
    }
}
