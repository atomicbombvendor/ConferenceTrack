package com.practice.model;

public class Conference {

    private String name;

    private String title;

    private Integer takeTime;

    /**
     * Conference will be order by priority asc.
     */
    private Integer priority;

    /**
     * If has been plan
     */
    private Boolean ifPlaned;

    private Integer order;
    /**
     * conference schedule time; HH:mm AM/PM
     */
    private String scheduleTime;

    public Conference(String name, String title, Integer takeTime){
        this.name = name;
        this.title = title;
        this.takeTime = takeTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getTakeTime() {
        return takeTime;
    }

    public void setTakeTime(Integer takeTime) {
        this.takeTime = takeTime;
    }

    public Integer getPriority() {
        return priority;
    }

    public Boolean getIfPlaned() {
        return ifPlaned;
    }

    public void setIfPlaned(Boolean ifPlaned) {
        this.ifPlaned = ifPlaned;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public String getScheduleTime() {
        return scheduleTime;
    }

    public void setScheduleTime(String scheduleTime) {
        this.scheduleTime = scheduleTime;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Conference{" +
                "name='" + name + '\'' +
                ", title='" + title + '\'' +
                ", takeTime=" + takeTime +
                ", priority=" + priority +
                ", ifPlaned=" + ifPlaned +
                ", order=" + order +
                ", scheduleTime='" + scheduleTime + '\'' +
                '}';
    }

    public String printTrack(){
        String format;
        if (takeTime == 0){
            format = "%s %s";
        }else{
            format = "%s %s %d minutes";
        }

        return String.format(format, this.getScheduleTime(), this.getTitle(), this.getTakeTime());
    }
}