package com.practice.service.impl;


import com.practice.common.ConferenceTakeTimesEnum;
import com.practice.model.Conference;
import com.practice.service.TackService;
import com.practice.util.ConferenceUtil;
import com.practice.util.RegexUtil;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class TaskServiceImpl implements TackService {

    @Override
    public List<List<Conference>> getSessionDays(List<Conference> sequentialConferences) {

        List<Conference> conferences = sequentialConferences;

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
