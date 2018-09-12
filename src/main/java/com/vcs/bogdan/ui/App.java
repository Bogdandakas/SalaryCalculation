package com.vcs.bogdan.ui;

import com.vcs.bogdan.beans.*;
import com.vcs.bogdan.service.db.*;
import com.vcs.bogdan.service.*;

import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.vcs.bogdan.beans.Contract.CONTRACT;
import static com.vcs.bogdan.beans.Contract.PERSON_ID;
import static com.vcs.bogdan.beans.LogHandler.*;
import static com.vcs.bogdan.beans.Period.PERIOD;
import static com.vcs.bogdan.beans.TimeList.TIME_LIST;

public class App {

    private static Scanner scanner = new Scanner(System.in);
    private static Logger logger = Logger.getLogger(LogHandler.class.getName());

    public static void main(String[] args) {
        app();
    }

    private static void app() {
        logger.log(Level.INFO, START_APP);
        AppService service = new AppService();
        service.start();

        run();

        scanner.close();
        service.close();
        logger.log(Level.INFO, CLOSE_APP);
    }

    private static void run() {
        boolean isOn = true;
        while (isOn) {
            logger.log(Level.INFO, MENU_EXIT + SPACE +
                    MENU_PERIOD + SPACE +
                    MENU_PERSON + SPACE +
                    MENU_CONTRACT + SPACE +
                    MENU_TIME_LIST + SPACE +
                    MENU_PAY_ROLL + SPACE + MENU_CALC_SALARY);

            int input = scanner.nextInt();
            switch (input) {
                case 0:
                    isOn = false;
                    break;
                case 1:
                    PeriodService periodService = new PeriodService();
                    loggPeriod(periodService.getAll());
                    break;
                case 2:
                    PersonService personService = new PersonService();
                    loggPerson(personService.getAll());
                    break;
                case 3:
                    ContractService contractService = new ContractService();
                    loggContract(contractService.getAll());
                    break;
                case 4:
                    TimeListService timeListService = new TimeListService();
                    loggTimeList(timeListService.getAll());
                    break;
                case 5:
                    PayRollService payRollService = new PayRollService();
                    loggPayRoll(payRollService.getAll());
                    break;
                case 6:
                    logger.log(Level.INFO, INPUT_PERIOD_ID);
                    String periodId = scanner.next();
                    CalcService calcService = new CalcService();
                    List<PayRoll> payRolls = calcService.calcAll(periodId);
                    calcService.register(payRolls);
                    break;
                default:
                    break;
            }
        }
    }

    private static void loggPerson(List<Person> persons) {
        for (Person p : persons) {
            logger.log(Level.INFO, PERIOD + p.getId() + SPACE + p.getName() + SPACE + p.getSurname());
        }
    }

    private static void loggContract(List<Contract> contracts) {
        for (Contract c : contracts) {
            logger.log(Level.INFO, CONTRACT + c.getDate() +
                    SPACE + c.getEvent() +
                    SPACE + c.getType() +
                    SPACE + c.getDayHours() +
                    SPACE + c.getWage() +
                    SPACE + c.isMain());

        }
    }

    private static void loggPeriod(List<Period> periods) {
        for (Period p : periods) {
            logger.log(Level.INFO, PERIOD + p.getId() +
                    SPACE + p.getWorkDays() +
                    SPACE + p.getWorkHours() +
                    SPACE + p.getHourlyMin() +
                    SPACE + p.getMin() +
                    SPACE + p.getMoreTimeCoefficient() +
                    SPACE + p.getRedDayCoefficient() + "\n" +
                    TAX_LOG + p.getTaxFree() +
                    SPACE + p.getPercent() +
                    SPACE + p.getBase() +
                    SPACE + p.getCoefficient() +
                    SPACE + p.getPnpd() + "\n" +
                    INSURANCE_LOG + p.getHealthEmployee() +
                    SPACE + p.getHealthNewEmployee() +
                    SPACE + p.getHealthEmployer() +
                    SPACE + p.getSocialEmployee() +
                    SPACE + p.getSocialEmployer() +
                    SPACE + p.getGuaranteeFund() +
                    SPACE + p.getSickPayCoefficient() +
                    SPACE + p.getSickPayDay());
        }
    }

    private static void loggTimeList(List<TimeList> timeLists) {
        for (int i = 0; i < timeLists.size(); i++) {
            logger.log(Level.INFO, TIME_LIST + timeLists.get(i).getPersonId() +
                    SPACE + timeLists.get(i).getDate() +
                    SPACE + timeLists.get(i).getEvent() +
                    SPACE + timeLists.get(i).getValue());
        }
    }

    private static void loggPayRoll(List<PayRoll> payRolls) {
        logger.log(Level.INFO, PERIOD + payRolls.get(0).getPeriodId());
        for (PayRoll p : payRolls) {
            logger.log(Level.INFO, PERSON_ID + SPACE + p.getPersonId() +
                    IN_LOG + p.getIncome() +
                    TAX_LOG + p.getTax() +
                    INSURANCE_LOG + p.getInsurance() +
                    OUT_LOG + p.getOutcome());
        }
    }

}
