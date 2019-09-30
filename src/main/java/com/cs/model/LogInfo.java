package com.cs.model;

public class LogInfo {
    private String id;
    private String state;
    private String type;
    private String host;
    private long timestamp;
    private long eventDuration;
    private boolean alert;
    private long startTime;
    private long EndTime;
    public LogInfo (){}

    public LogInfo(String id, String state, long timestamp) {
        this.id = id;
        this.state = state;
        this.timestamp = timestamp;
    }

    public boolean isAlert() {
        return alert;
    }

    public void setAlert(boolean alert) {
        this.alert = alert;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }


    public long getEventDuration() {
        return eventDuration;
    }

    public void setEventDuration(long eventDuration) {
        this.eventDuration = eventDuration;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return EndTime;
    }

    public void setEndTime(long endTime) {
        EndTime = endTime;
    }
}
