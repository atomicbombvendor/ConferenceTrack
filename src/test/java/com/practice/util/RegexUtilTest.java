package com.practice.util;

import com.practice.model.Conference;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class RegexUtilTest {

    @Test
    public void getConference() {

        String input = "Writing4 Fast Tests Against Enterprise Rails 60min";
        Conference resultc = RegexUtil.getConference(input);

        System.out.println(resultc.toString());
    }
}