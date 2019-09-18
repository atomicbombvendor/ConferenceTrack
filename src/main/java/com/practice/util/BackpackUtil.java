package com.practice.util;

import com.practice.model.Conference;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

/**
 * @author haoyue
 */
public class BackpackUtil {

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

    public static void removePlanedConference(List<Conference> conferences){

        conferences.removeIf(Conference::getIfPlaned);
    }

    public static void setScheduleTime(List<Conference> conferences, boolean ifMorning){
        LocalDateTime startTime;
        LocalDateTime now = LocalDateTime.now(ZoneId.of("UTC"));
        int lunchHour = 12;
        int networkHour = 16;
        int morningStart = 9;
        int afternoonStart = 13;
        int minuteStart = 0, secondStart = 0;

        if (ifMorning){
            startTime = LocalDateTime.of(now.getYear(), now.getMonth(), now.getDayOfMonth(), morningStart, minuteStart, secondStart);
        }else{
            startTime = LocalDateTime.of(now.getYear(), now.getMonth(), now.getDayOfMonth(), afternoonStart, minuteStart, secondStart);
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

            if (startTime.getHour() < lunchHour){
                startTime = LocalDateTime.of(now.getYear(), now.getMonth(), now.getDayOfMonth(), lunchHour, minuteStart, secondStart);
            }
        }else{
            conference = new Conference("Networking Event", "Networking Event", 0);

            // after session end before 16:00
            if (startTime.getHour() < networkHour){
                startTime = LocalDateTime.of(now.getYear(), now.getMonth(), now.getDayOfMonth(), networkHour, minuteStart, secondStart);
            }
        }
        conference.setScheduleTime(startTime.format(dateFormat));
        conferences.add(conference);
    }
}
