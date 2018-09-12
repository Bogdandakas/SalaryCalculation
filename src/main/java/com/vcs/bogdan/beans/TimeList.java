package com.vcs.bogdan.beans;

import com.vcs.bogdan.beans.enums.WorkEvent;

public class TimeList {

    public static final String TIME_LIST = "Time list: ";
    public static final String TABLE = "timeList";
    public static final String ID = "id";
    public static final String DATE = "date";
    public static final String PERSON_ID = "personId";
    public static final String EVENT = "event";
    public static final String VALUE = "value";

    private String id;
    private long date;
    private String personId;
    private WorkEvent event;
    private double value;

    public TimeList() {
    }

    public TimeList(long date, String personId, WorkEvent event, double value) {
        this.date = date;
        this.personId = personId;
        this.event = event;
        this.value = value;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getPersonId() {
        return personId;
    }

    public WorkEvent getEvent() {
        return event;
    }

    public void setEvent(String str) {
        this.event = WorkEvent.valueOf(str);
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}
