package com.practice.common;

public enum ConferenceTakeTimesEnum {

    LIGHTNING("lightning", 5),
    MORNING_SESSION("morning", 3*60),
    AFTER_MAX_SESSION("after_max", 4*60),
    AFTER_MIN_SESSION("after_min", 3*60),
    ALL_DAY_MAX("all_max", (3*60) + (4*60)),
    ALL_DAY_MIN("all_min", (3*60) + (3*60));

    private String name;

    private Integer value;

    ConferenceTakeTimesEnum(String name, Integer value){
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }
}
