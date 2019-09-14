package com.practice.poc;

import org.junit.Test;

import static org.junit.Assert.*;

public class ConferenceManagerTest {

    @Test
    public void scheduleConference() {

        String inputFile = this.getClass().getResource("/") + "Conferences.txt";
        inputFile = inputFile.replace("file:/", "");
        ConferenceManager conferenceManager = new ConferenceManager();
        try{
            conferenceManager.scheduleConference(inputFile);
        }catch(ConferenceManager.InvalidTalkException ite) {
            ite.printStackTrace();
        }catch(Exception e) {
            e.printStackTrace();
        }
    }
}