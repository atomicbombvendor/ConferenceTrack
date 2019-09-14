package com.practice.comparator;

import com.practice.model.Conference;

import java.util.Comparator;

public class ConferenceComparator implements Comparator<Conference> {

    @Override
    public int compare(Conference c1, Conference c2) {

        if (c1 == null && c2 != null) {
            return 1;
        }

        if (c2 == null && c1 != null) {
            return 1;
        }

        if (c1 == null) {
            return 0;
        }

        return -1 * (c1.getPriority() - c2.getPriority());
    }
}
