package com.vcs.bogdan.beans;

public class PayRoll {

    public static final String TABLE = "payRoll";
    public static final String ID = "id";
    public static final String PERIOD_ID = "periodId";
    public static final String PERSON_ID = "personId";
    public static final String INCOME = "income";
    public static final String TAX = "tax";
    public static final String INSURANCE = "insurance";
    public static final String OUTCOME = "outcome";

    private String id;
    private String periodId;
    private String personId;
    private double income;
    private double tax;
    private double insurance;
    private double outcome;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPeriodId() {
        return periodId;
    }

    public void setPeriodId(String periodId) {
        this.periodId = periodId;
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public double getIncome() {
        return income;
    }

    public void setIncome(double income) {
        this.income = income;
    }

    public double getTax() {
        return tax;
    }

    public void setTax(double tax) {
        this.tax = tax;
    }

    public double getInsurance() {
        return insurance;
    }

    public void setInsurance(double insurance) {
        this.insurance = insurance;
    }

    public double getOutcome() {
        return outcome;
    }

    public void setOutcome(double outcome) {
        this.outcome = outcome;
    }
}
