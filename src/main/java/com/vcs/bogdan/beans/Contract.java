package com.vcs.bogdan.beans;

import com.vcs.bogdan.beans.enums.CalcType;
import com.vcs.bogdan.beans.enums.ContractEvent;

import static com.vcs.bogdan.beans.enums.CalcType.*;
import static com.vcs.bogdan.beans.enums.ContractEvent.*;

public class Contract {

    public static final String CONTRACT = "Contract: ";
    public static final String TABLE = "contract";
    public static final String ID = "id";
    public static final String PERSON_ID = "personId";
    public static final String DATE = "date";
    public static final String EVENT = "event";
    public static final String TYPE = "type";
    public static final String DAY_HOURS = "dayHours";
    public static final String WAGE = "wage";
    public static final String IS_MAIN = "isMain";

    private String id;
    private String personId;
    private long date;
    private ContractEvent event;
    private CalcType type;
    private double dayHours;
    private double wage;
    private boolean isMain;

    public Contract() {
    }

    public Contract(String personId) {
        setPersonId(personId);
        setDate(20180101);
        setEvent(UPDATE);
        setType(HOUR);
        setDayHours(8);
        setWage(8.4);
        setMain(true);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public ContractEvent getEvent() {
        return event;
    }

    public void setEvent(ContractEvent event) {
        this.event = event;
    }

    public void setEvent(String str) {
        this.event = ContractEvent.valueOf(str);
    }

    public CalcType getType() {
        return type;
    }

    public void setType(CalcType type) {
        this.type = type;
    }

    public void setType(String type) {
        this.type = CalcType.valueOf(type);
    }

    public double getDayHours() {
        return dayHours;
    }

    public void setDayHours(double dayHours) {
        this.dayHours = dayHours;
    }

    public double getWage() {
        return wage;
    }

    public void setWage(double wage) {
        this.wage = wage;
    }

    public boolean isMain() {
        return isMain;
    }

    public void setMain(boolean main) {
        isMain = main;
    }

}
