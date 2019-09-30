package com.cs.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "LOGALERT")
public class LogInfoEntity {
    @Id
    @Column
    private String eventId;
    @Column
    private String type;
    @Column
    private String host;
    @Column
    private long eventDuration;
    @Column
    private boolean alert;
    @Column
    private long startTime;
    @Column
    private long EndTime;

    public LogInfoEntity(String eventId, String type, String host, long eventDuration, boolean alert, long startTime, long endTime) {
        this.eventId = eventId;
        this.type = type;
        this.host = host;
        this.eventDuration = eventDuration;
        this.alert = alert;
        this.startTime = startTime;
        EndTime = endTime;
    }

    public LogInfoEntity() {
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
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

    public long getEventDuration() {
        return eventDuration;
    }

    public void setEventDuration(long eventDuration) {
        this.eventDuration = eventDuration;
    }

    public boolean isAlert() {
        return alert;
    }

    public void setAlert(boolean alert) {
        this.alert = alert;
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
