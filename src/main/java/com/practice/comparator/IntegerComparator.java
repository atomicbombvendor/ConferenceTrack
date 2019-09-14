package com.practice.comparator;

import java.util.Comparator;

public class IntegerComparator implements Comparator<Integer> {

    @Override
    public int compare(Integer val1, Integer val2) {

        if (val1 == null && val2 != null) {
            return 1;
        }

        if (val2 == null && val1 != null) {
            return 1;
        }

        if (val1 == null) {
            return 0;
        }

        return (val1 - val2);
    }
}
