package com.vcs.bogdan.service;

import com.vcs.bogdan.beans.*;
import com.vcs.bogdan.service.db.*;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.vcs.bogdan.beans.LogHandler.*;

public class CalcService {

    private static final int TEN = 10;
    private static final int HUNDRED = 100;
    private static final int DECIMAL_PLACE = 2;
    private static final double CORRECTION = 0.5;

    Logger logger = Logger.getLogger(LogHandler.class.getName());

    public List<PayRoll> calcAll(String periodId) {

        List<PayRoll> result = new ArrayList<>();

        Period period = new PeriodService().get(periodId);
        List<TimeList> timeLists = new TimeListService().getAll();

        ContractService contractService = new ContractService();

        PersonService personService = new PersonService();
        for (Person person : personService.getAll()) {
            Contract contract = contractService.getValidateContract(periodId, person.getId());
            if (contract.getId() != null)
                result.add(calc(period, contract, timeLists));
        }
        return result;
    }

    public void register(List<PayRoll> payRolls) {

        if (null != payRolls && !payRolls.isEmpty()) {
            PayRollService service = new PayRollService();
            for (PayRoll payRoll : payRolls) {
                service.add(payRoll);
            }
        }
        logger.log(Level.INFO, ADD_DATA + payRolls.get(0).getPeriodId());

    }

    public PayRoll calc(Period period, Contract contract, List<TimeList> timeLists) {

        PayRoll result = new PayRoll();

        result.setPeriodId(period.getId());
        if (contract.getDate() != 0) {
            result.setPeriodId(period.getId());
            result.setPersonId(contract.getPersonId());
            result.setIncome(getIncome(period, contract, timeLists));
            result.setTax(getIncomeTax(period, contract, result.getIncome()));

            double social = getSocialInsuranceDeductionFromEmployee(period, contract, result.getIncome());
            double health = getHealthInsuranceDeductionFromEmployee(period.getHealthEmployee(), result.getIncome());
            double guaranteeFund = getGuaranteeFundDeductionSum(period.getGuaranteeFund(), result.getIncome());
            result.setInsurance(round(social + health + guaranteeFund, DECIMAL_PLACE));

            result.setOutcome(result.getIncome() - result.getTax() - result.getInsurance());
        }
        logger.log(Level.INFO, CALC + result.getId());
        return result;
    }

    private double getIncome(Period period, Contract contract, List<TimeList> timeLists) {
        return Math.max(calcIncomeWage(period, contract, timeLists), period.getMin());
    }

    private double getIncomeTax(Period period, Contract contract, double incomeWage) {
        double taxFree = round(getCalcTaxFree(incomeWage, contract, period), DECIMAL_PLACE);
        return round((incomeWage - taxFree) * period.getPercent() / HUNDRED, DECIMAL_PLACE);
    }

    private double getSocialInsuranceDeductionFromEmployee(Period period, Contract contract, double incomeWage) {
        return getCalculatePercentageFromNumber(incomeWage, period.getSocialEmployee());
    }

    private double getHealthInsuranceDeductionFromEmployee(double healthEmploye, double incomeWage) {
        return getCalculatePercentageFromNumber(incomeWage, healthEmploye);
    }

    private double getGuaranteeFundDeductionSum(double guaranteeFund, double incomeWage) {
        return getCalculatePercentageFromNumber(incomeWage, guaranteeFund);
    }

    private double calcIncomeWage(Period period, Contract contract, List<TimeList> timeLists) {

        switch (contract.getType()) {
            case DAY:
                return getMultiply(new TimeListService().getDays(timeLists, contract.getPersonId(), period.getId()), contract.getWage());
            case HOUR:
                return getMultiply(new TimeListService().getHours(timeLists, contract.getPersonId(), period.getId()), contract.getWage());
            case MONTH:
                return getMultiply(1, contract.getWage());
            default:
                return 0;
        }
    }

    private double getCalculatePercentageFromNumber(double incomeWage, double percent) {
        return round(incomeWage * percent / HUNDRED, DECIMAL_PLACE);
    }

    private double round(double number, int decPlace) {
        double tempNr = Math.pow(TEN, decPlace);
        return Math.floor(number * tempNr + CORRECTION) / tempNr;
    }

    private double getCalcTaxFree(double incomeWage, Contract contract, Period period) {
        double result = period.getTaxFree();
        if (contract.isMain()) {
            if (incomeWage >= period.getBase()) {
                result = result - (incomeWage - period.getBase()) * period.getCoefficient();
            }
        } else {
            result = 0;
        }
        return result;
    }

    private double getMultiply(double data, double wage) {
        return data * wage;
    }

}
