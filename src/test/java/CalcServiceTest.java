import com.vcs.bogdan.beans.Contract;
import com.vcs.bogdan.beans.PayRoll;
import com.vcs.bogdan.beans.Period;
import com.vcs.bogdan.beans.TimeList;
import com.vcs.bogdan.service.CalcService;
import com.vcs.bogdan.service.db.TimeListService;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static com.vcs.bogdan.beans.enums.CalcType.HOUR;
import static com.vcs.bogdan.beans.enums.ContractEvent.START;
import static com.vcs.bogdan.beans.enums.WorkEvent.H;
import static junit.framework.Assert.assertEquals;

public class CalcServiceTest {

    private CalcService service;

    @Before
    public void init() {
        service = new CalcService();
    }

    @Test(expected = RuntimeException.class)
    public void getIncomeTaxText() {

        TimeListService timeListService = new TimeListService();

    }

    @Test
    public void calcSuccess() {

        Period period = new Period();
        period.setId("201801");
        period.setWorkDays(21);
        period.setWorkHours(168);
        period.setMin(400);
        period.setHourlyMin(4.25);
        period.setMoreTimeCoefficient(1.5);
        period.setRedDayCoefficient(2);
        period.setTaxFree(380);
        period.setCoefficient(0.5);
        period.setBase(400);
        period.setPercent(15);
        period.setPnpd(0);
        period.setHealthEmployee(3);
        period.setHealthNewEmployee(3);
        period.setHealthEmployer(3);
        period.setSocialEmployee(6);
        period.setSocialEmployer(27.98);
        period.setGuaranteeFund(2);
        period.setSickPayDay(2);
        period.setSickPayCoefficient(0.8);

        Contract contract = new Contract();
        contract.setPersonId("112");
        contract.setDate(20180101);
        contract.setEvent(START);
        contract.setType(HOUR);
        contract.setDayHours(8);
        contract.setWage(0);
        contract.setMain(true);

        List<TimeList> timeLists = new ArrayList<>();
        timeLists.add(new TimeList(20180102, "112", H, 8));
        timeLists.add(new TimeList(20180107, "112", H, 4));

        PayRoll payRoll = service.calc(period, contract, timeLists);

        assertEquals( "201801", payRoll.getPeriodId());
        assertEquals( "112", payRoll.getPersonId());
        assertEquals( 400.0, payRoll.getIncome());
        assertEquals( 3.0, payRoll.getTax());
        assertEquals( 44.0, payRoll.getInsurance());
        assertEquals( 353.0, payRoll.getOutcome());
    }
}
