package com.practice.service.impl;


import com.practice.common.ConferenceTakeTimesEnum;
import com.practice.comparator.ConferenceComparator;
import com.practice.comparator.IntegerComparator;
import com.practice.exception.InputIllegalException;
import com.practice.model.Conference;
import com.practice.service.ConferenceTackService;
import com.practice.util.ConferenceUtil;
import com.practice.util.RegexUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ConferenceTaskServiceImpl implements ConferenceTackService {

    @Override
    public List<Conference> getSequentialConferences(List<String> inputLines) {

        List<Conference> conferences = new ArrayList<>();
        for (String line : inputLines){
            conferences.add(RegexUtil.getConference(line));
        }

        setPriority(conferences);
        conferences.sort(new ConferenceComparator());
        return conferences;
    }

    @Override
    public List<List<Conference>> getSessionDays(List<Conference> sequentialConferences) {

        List<Conference> conferences = sequentialConferences;

        //todo 是否只需要两天的时间
        if (ifAllTakeTimeIsMoreTwoDays(conferences)){
            throw new InputIllegalException("Total minutes of conference is more than tow days");
        }

        int order = 10;

        List<List<Conference>> sessions = new ArrayList<>();

        while(conferences.size() > 0) {

            int numberOfConference = conferences.size();
            InputParameter input = new InputParameter(order);
            int totalMinutes = input.getTotalMinutes();
            boolean ifMorning = input.isIfMorning();

            int[][] table = ConferenceUtil.init(numberOfConference, totalMinutes);
            List<Conference> session = new ArrayList<>();
            ConferenceUtil.findScheduled(table, totalMinutes, numberOfConference, conferences);
            ConferenceUtil.findTrack(table, numberOfConference, totalMinutes, conferences, order, session);
            ConferenceUtil.removePlanedConference(conferences);
            ConferenceUtil.setScheduleTime(session, ifMorning);
            sessions.add(session);
            order += 10;
        }
        return sessions;
    }

    /**
     * priority get from take times.
     * Longer take time with bigger value of priority;
     * @param conferences
     * @return
     */
    private void setPriority(List<Conference> conferences){

        List<Integer> takeTimeList = conferences.stream().map(Conference::getTakeTime).distinct()
                .sorted(new IntegerComparator()).collect(Collectors.toList());

        for (Conference tmp : conferences) {
//            tmp.setPriority(takeTimeList.indexOf(tmp.getTakeTime()) + 1);
            tmp.setPriority((tmp.getTakeTime()/5));
        }
    }

    /**
     * only two day.
     * @param conferences
     * @return
     */
    private boolean ifAllTakeTimeIsMoreTwoDays(List<Conference> conferences){

        Integer allTimeTakes = 0;

        for (Conference c : conferences){
            allTimeTakes += c.getTakeTime();
        }
        return (allTimeTakes / ConferenceTakeTimesEnum.ALL_DAY_MAX.getValue()) > 2;
    }

    private static class InputParameter {

        private int totalMinutes;

        private boolean ifMorning;

        private int order;

        InputParameter(int order){
            Integer morningTotalMinutes = ConferenceTakeTimesEnum.MORNING_SESSION.getValue();
            Integer afternoonTotalMinutes = ConferenceTakeTimesEnum.AFTER_MAX_SESSION.getValue();

            if ((order/10)%2 == 0){
                this.totalMinutes = afternoonTotalMinutes;
                this.ifMorning = false;
            }else{
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
