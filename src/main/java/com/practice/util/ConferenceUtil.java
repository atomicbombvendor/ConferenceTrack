package com.practice.util;

import com.practice.common.ConferenceTakeTimesEnum;
import com.practice.model.Conference;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ConferenceUtil {

//    public static List<List<Conference>> getSessionDays(List<Conference> conferences) {
//
//        Integer morningTotalMinutes = ConferenceTakeTimesEnum.MORNING_SESSION.getValue();
//        Integer afternoonTotalMinutes = ConferenceTakeTimesEnum.AFTER_MAX_SESSION.getValue();
//        int order = 10;
//
//        List<List<Conference>> sessions = new ArrayList<>();
//
//        while(conferences.size() > 0) {
//
//            int totalMinutes;
//            int numberOfConference = conferences.size();
//            if ((order/10)%2 == 0){
//                System.out.println("get afternoon schedule");
//                totalMinutes = afternoonTotalMinutes;
//            }else{
//                System.out.println("get morning schedule");
//                totalMinutes = morningTotalMinutes;
//            }
//
//            int[][] table = init(numberOfConference, totalMinutes);
//            List<Conference> session = new ArrayList<>();
//            findScheduled(table, totalMinutes, numberOfConference, conferences);
//            findTrack(table, numberOfConference, totalMinutes, conferences, order, session);
//            removePlanedConference(conferences);
//            sessions.add(session);
//            order += 10;
//        }
//
//        return sessions;
//    }

    public Integer sumNotPlanedTakeTimes(List<Conference> conferences) {

        Integer takeTimes = 0;

        for (Conference c : conferences) {
            if (!c.getIfPlaned()) {
                takeTimes = c.getTakeTime();
            }
        }

        return takeTimes;
    }

    private static Integer getPriority(List<Conference> conferences) {

        for (Conference c : conferences) {
            if (!c.getIfPlaned()) {
                return c.getPriority();
            }
        }

        return 0;
    }

    /**
     * 根据权重和是否被安排过查找
     * @param conferences
     * @param priority
     * @return
     */
    private static Conference getConferenceByPriority(List<Conference> conferences, Integer priority) {

        for (Conference c : conferences) {
            if (!c.getIfPlaned() && c.getPriority().equals(priority)) {
                return c;
            }
        }
        return null;
    }

    private static Integer getMaxPriority(List<Conference> conferences){

        return conferences.get(conferences.size()-1).getPriority();
    }

//    public static void backpack1(List<Conference> conferences){
//        int remainSpace = totalMinutes;
//        int numberOfConference = conferences.size();
//        int[][] table = init(numberOfConference, remainSpace);
//        int order = 10;
//        List<List<Conference>> sessions = new ArrayList<>();
//
//        while(conferences.size() > 0) {
//            List<Conference> session = new ArrayList<>();
//            findScheduled(table, totalMinutes, numberOfConference, conferences);
//            findTrack(table, numberOfConference, totalMinutes, conferences, order, session);
//            sessions.add(session);
//            order += 10;
//        }
//    }

    /**
     * table
     * @param table
     * @param totalMinutes
     * @param totalNumber
     * @param conferences
     */
    public static void findScheduled(int[][] table, int totalMinutes, int totalNumber, List<Conference> conferences) {
        int currentConference, currentMinutes;

        for(currentConference = 1; currentConference <= totalNumber; currentConference++) {

            int takeTime = conferences.get(currentConference-1).getTakeTime();
            int value = conferences.get(currentConference-1).getPriority();
            for(currentMinutes=1; currentMinutes <= totalMinutes; currentMinutes++) {
                // current capacity is less than current conference take time
                if(currentMinutes < takeTime){
                    table[currentConference][currentMinutes] = table[currentConference-1][currentMinutes];
                }
                else {
                    //不装价值大
                    //前i-1个物品的最优解与第i个物品的价值之和更大
                    table[currentConference][currentMinutes] = Math.max(table[currentConference - 1][currentMinutes],
                            table[currentConference - 1][currentMinutes - takeTime] + value);
                }
            }
        }
    }


    public static void findTrack(int[][] table, int currentIndex, int currentMinutes, List<Conference> conferences, int order, List<Conference> session) {

        if(currentIndex >= 1) {

            int conferenceIndex = currentIndex - 1;
            int weight = conferences.get(conferenceIndex).getTakeTime();
            int value = conferences.get(conferenceIndex).getPriority();

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

        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("hh:mm a");
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
