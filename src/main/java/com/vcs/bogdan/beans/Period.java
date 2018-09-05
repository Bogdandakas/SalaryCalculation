package com.vcs.bogdan.beans;

public class Period {

    public static final String PERIOD = "Period: ";
    public static final String TABLE = "period";
    public static final String ID = "id";
    public static final String WORK_DAYS = "workDays";
    public static final String WORK_HOURS = "workHours";
    public static final String MIN = "min";
    public static final String HOURLY_MIN = "hourlyMin";
    public static final String MORE_TIME = "moreTimeCoefficient";
    public static final String RED_DAY = "redDayCoefficient";
    public static final String TAX_FREE = "taxFree";
    public static final String TAX_COEFFICIENT = "coefficient";
    public static final String BASE = "base";
    public static final String TAX_PERCENT = "percent";
    public static final String PNPD = "pnpd";
    public static final String HEALT_EE = "healthEmployee";
    public static final String HEALT_NEE = "healthNewEmployee";
    public static final String HEALTH_ER = "healthEmployer";
    public static final String SOCIAL_EE = "socialEmployee";
    public static final String SOCIAL_ER = "socialEmployer";
    public static final String GF = "guaranteeFund";
    public static final String SICK_PAY_DAY = "sickPayDay";
    public static final String SIC_PAY_COEFFICIENT = "sickPayCoefficient";

    private String id;
    private int workDays;
    private int workHours;
    private double min;
    private double hourlyMin;
    private double moreTimeCoefficient;
    private double redDayCoefficient;
    private double taxFree;
    private double coefficient;
    private double base;
    private double percent;
    private double pnpd;
    private double healthEmployee;
    private double healthNewEmployee;
    private double healthEmployer;
    private double socialEmployee;
    private double socialEmployer;
    private double guaranteeFund;
    private int sickPayDay;
    private double sickPayCoefficient;

    public Period() {
    }

    public Period(String id) {
        this.id = id;
        setWorkDays(21);
        setWorkHours(168);
        setMin(400);
        setHourlyMin(4.25);
        setMoreTimeCoefficient(1.5);
        setRedDayCoefficient(2);
        setTaxFree(380);
        setCoefficient(0.5);
        setBase(400);
        setPercent(15);
        setPnpd(0);
        setHealthEmployee(3);
        setHealthNewEmployee(3);
        setHealthEmployer(3);
        setSocialEmployee(6);
        setSocialEmployer(27.98);
        setGuaranteeFund(2);
        setSickPayDay(2);
        setSickPayCoefficient(0.8);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getWorkDays() {
        return workDays;
    }

    public void setWorkDays(int workDays) {
        this.workDays = workDays;
    }

    public int getWorkHours() {
        return workHours;
    }

    public void setWorkHours(int workHours) {
        this.workHours = workHours;
    }

    public double getMin() {
        return min;
    }

    public void setMin(double min) {
        this.min = min;
    }

    public double getHourlyMin() {
        return hourlyMin;
    }

    public void setHourlyMin(double hourlyMin) {
        this.hourlyMin = hourlyMin;
    }

    public double getMoreTimeCoefficient() {
        return moreTimeCoefficient;
    }

    public void setMoreTimeCoefficient(double moreTimeCoefficient) {
        this.moreTimeCoefficient = moreTimeCoefficient;
    }

    public double getRedDayCoefficient() {
        return redDayCoefficient;
    }

    public void setRedDayCoefficient(double redDayCoefficient) {
        this.redDayCoefficient = redDayCoefficient;
    }

    public double getTaxFree() {
        return taxFree;
    }

    public void setTaxFree(double taxFree) {
        this.taxFree = taxFree;
    }

    public double getCoefficient() {
        return coefficient;
    }

    public void setCoefficient(double coefficient) {
        this.coefficient = coefficient;
    }

    public double getBase() {
        return base;
    }

    public void setBase(double base) {
        this.base = base;
    }

    public double getPercent() {
        return percent;
    }

    public void setPercent(double percent) {
        this.percent = percent;
    }

    public double getPnpd() {
        return pnpd;
    }

    public void setPnpd(double pnpd) {
        this.pnpd = pnpd;
    }

    public double getHealthEmployee() {
        return healthEmployee;
    }

    public void setHealthEmployee(double healthEmployee) {
        this.healthEmployee = healthEmployee;
    }

    public double getHealthNewEmployee() {
        return healthNewEmployee;
    }

    public void setHealthNewEmployee(double healthNewEmployee) {
        this.healthNewEmployee = healthNewEmployee;
    }

    public double getHealthEmployer() {
        return healthEmployer;
    }

    public void setHealthEmployer(double healthEmployer) {
        this.healthEmployer = healthEmployer;
    }

    public double getSocialEmployee() {
        return socialEmployee;
    }

    public void setSocialEmployee(double socialEmployee) {
        this.socialEmployee = socialEmployee;
    }

    public double getSocialEmployer() {
        return socialEmployer;
    }

    public void setSocialEmployer(double socialEmployer) {
        this.socialEmployer = socialEmployer;
    }

    public double getGuaranteeFund() {
        return guaranteeFund;
    }

    public void setGuaranteeFund(double guaranteeFund) {
        this.guaranteeFund = guaranteeFund;
    }

    public int getSickPayDay() {
        return sickPayDay;
    }

    public void setSickPayDay(int sickPayDay) {
        this.sickPayDay = sickPayDay;
    }

    public double getSickPayCoefficient() {
        return sickPayCoefficient;
    }

    public void setSickPayCoefficient(double sickPayCoefficient) {
        this.sickPayCoefficient = sickPayCoefficient;
    }
}
