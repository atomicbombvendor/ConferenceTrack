package com.practice.util;

import com.practice.model.Conference;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

public class ConferenceUtil {

    public Integer sumNotPlanedTakeTimes(List<Conference> conferences) {

        Integer takeTimes = 0;

        for (Conference c : conferences) {
            if (!c.getIfPlaned()) {
                takeTimes = c.getTakeTime();
            }
        }

        return takeTimes;
    }

    public static Integer getPriority(List<Conference> conferences) {

        for (Conference c : conferences) {
            if (!c.getIfPlaned()) {
                return c.getValue();
            }
        }

        return 0;
    }

    public static Conference getConferenceByPriority(List<Conference> conferences, Integer priority) {

        for (Conference c : conferences) {
            if (!c.getIfPlaned() && c.getValue().equals(priority)) {
                return c;
            }
        }
        return null;
    }

    public static Integer getMaxPriority(List<Conference> conferences){

        return conferences.get(conferences.size()-1).getValue();
    }

    /**
     * 01 backpack to find session
     * @param table
     * @param totalMinutes
     * @param totalNumber
     * @param conferences
     */
    public static void findScheduled(int[][] table, int totalMinutes, int totalNumber, List<Conference> conferences) {
        int currentConference, currentMinutes;

        for(currentConference = 1; currentConference <= totalNumber; currentConference++) {

            int takeTime = conferences.get(currentConference-1).getTakeTime();
            int value = conferences.get(currentConference-1).getValue();
            for(currentMinutes=1; currentMinutes <= totalMinutes; currentMinutes++) {
                // current capacity is less than current conference take time
                if(currentMinutes < takeTime){
                    table[currentConference][currentMinutes] = table[currentConference-1][currentMinutes];
                }
                else {
                    //which is max value for scheduled or not schedule current conference
                    table[currentConference][currentMinutes] = Math.max(table[currentConference - 1][currentMinutes],
                            table[currentConference - 1][currentMinutes - takeTime] + value);
                }
            }
        }
    }


    /**
     * Track conference schedule
     * @param table
     * @param currentIndex
     * @param currentMinutes
     * @param conferences
     * @param order
     * @param session
     */
    public static void findTrack(int[][] table, int currentIndex, int currentMinutes, List<Conference> conferences, int order, List<Conference> session) {

        if(currentIndex >= 1) {

            int conferenceIndex = currentIndex - 1;
            int weight = conferences.get(conferenceIndex).getTakeTime();
            int value = conferences.get(conferenceIndex).getValue();

            if(table[currentIndex][currentMinutes] == table[currentIndex-1][currentMinutes]) {

                //find next
                conferences.get(conferenceIndex).setIfPlaned(false);
                findTrack(table,currentIndex - 1, currentMinutes, conferences, order, session);
            }
            else if( (currentMinutes-weight >= 0) &&
                    table[currentIndex][currentMinutes] == table[currentIndex-1][currentMinutes-weight] + value) {
                //has been scheduled
                conferences.get(conferenceIndex).setIfPlaned(true);
                conferences.get(conferenceIndex).setOrder(order);
                order++;
                session.add(conferences.get(conferenceIndex));
                // find next
                findTrack(table, currentIndex-1,currentMinutes-weight, conferences, order, session);
            }
        }
    }

    public static int[][] init(Integer conferenceSize, Integer space){

        int[][] table = new int[conferenceSize + 1][space + 1];
        for (int i=0; i <= table.length-1; i++){
            table[i][0] = 0;
        }

        for (int i=0; i <= table[0].length-1; i++){
            table[0][i] = 0;
        }
        return table;
    }

    public static void removePlanedConference(List<Conference> conferences){

        conferences.removeIf(Conference::getIfPlaned);
    }

    public static void setScheduleTime(List<Conference> conferences, boolean ifMorning){
        LocalDateTime startTime;

        if (ifMorning){
            LocalDateTime now = LocalDateTime.now(ZoneId.of("UTC"));
            startTime = LocalDateTime.of(now.getYear(), now.getMonth(), now.getDayOfMonth(), 9, 0,0);
        }else{
            LocalDateTime now = LocalDateTime.now();
            startTime = LocalDateTime.of(now.getYear(), now.getMonth(), now.getDayOfMonth(), 13, 0,0);
        }

        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("hh:mma", Locale.US);
        for (Conference conference : conferences){
            conference.setScheduleTime(startTime.format(dateFormat));
            int takeTime = conference.getTakeTime();
            startTime = startTime.plusMinutes(takeTime);
        }

        Conference conference;
        if (ifMorning){
            conference = new Conference("Lunch", "Lunch", 0);
            conference.setScheduleTime(startTime.format(dateFormat));
        }else{
            conference = new Conference("Networking Event", "Networking Event", 0);
            conference.setScheduleTime(startTime.format(dateFormat));
        }

        conferences.add(conference);
    }
}
