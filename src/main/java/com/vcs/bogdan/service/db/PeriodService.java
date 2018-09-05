package com.vcs.bogdan.service.db;

import com.mysql.jdbc.StringUtils;
import com.vcs.bogdan.beans.LogHandler;
import com.vcs.bogdan.beans.Period;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.vcs.bogdan.beans.Period.*;
import static com.vcs.bogdan.service.db.ConnectionServiceImpl.connection;
import static com.vcs.bogdan.service.db.ConnectionServiceImpl.preparedStatement;
import static com.vcs.bogdan.beans.LogHandler.*;

public class PeriodService implements DBService<Period> {

    private static final String INSERT = "INSERT INTO " + TABLE + " Values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    private static final String Q = " =?, ";
    private static final String Q_LAST = " =?";
    private static final String WHERE_ID = " WHERE id =";
    private static final String UPDATE = "UPDATE " + TABLE +
            " SET " + ID + Q + WORK_DAYS + Q + WORK_HOURS + Q + MIN + Q + HOURLY_MIN + Q + MORE_TIME + Q + RED_DAY + Q +
            TAX_FREE + Q + TAX_COEFFICIENT + Q + BASE + Q + TAX_PERCENT + Q + PNPD + Q +
            HEALT_EE + Q + HEALT_NEE + Q + HEALTH_ER + Q + SOCIAL_EE + Q + SOCIAL_ER + Q + GF + Q + SICK_PAY_DAY + Q + SIC_PAY_COEFFICIENT + Q_LAST + WHERE_ID;
    private static final String SELECT_ALL = "SELECT * FROM " + TABLE;
    private static final String SELECT = SELECT_ALL + WHERE_ID;
    private static final String DELETE = "DELETE FROM " + TABLE + WHERE_ID;

    Logger logger = Logger.getLogger(LogHandler.class.getName());

    @Override
    public Period get(String id) {
        Period result = new Period();
        try {
            preparedStatement = connection.prepareStatement(SELECT + id);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                result = setPeriod(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        logger.log(Level.INFO, GET_DATA + result.getId());
        return result;
    }

    @Override
    public void add(Period period) {
        String id = period.getId();
        String queryStatement = (StringUtils.isNullOrEmpty(get(id).getId()) ? INSERT : UPDATE + id);

        try {
            int index = 1;
            preparedStatement = connection.prepareStatement(queryStatement);
            preparedStatement.setObject(index++, id);
            preparedStatement.setObject(index++, period.getWorkDays());
            preparedStatement.setObject(index++, period.getWorkHours());
            preparedStatement.setObject(index++, period.getMin());
            preparedStatement.setObject(index++, period.getHourlyMin());
            preparedStatement.setObject(index++, period.getMoreTimeCoefficient());
            preparedStatement.setObject(index++, period.getRedDayCoefficient());
            preparedStatement.setObject(index++, period.getTaxFree());
            preparedStatement.setObject(index++, period.getCoefficient());
            preparedStatement.setObject(index++, period.getBase());
            preparedStatement.setObject(index++, period.getPercent());
            preparedStatement.setObject(index++, period.getPnpd());
            preparedStatement.setObject(index++, period.getHealthEmployee());
            preparedStatement.setObject(index++, period.getHealthNewEmployee());
            preparedStatement.setObject(index++, period.getHealthEmployer());
            preparedStatement.setObject(index++, period.getSocialEmployee());
            preparedStatement.setObject(index++, period.getSocialEmployer());
            preparedStatement.setObject(index++, period.getGuaranteeFund());
            preparedStatement.setObject(index++, period.getSickPayCoefficient());
            preparedStatement.setObject(index, period.getRedDayCoefficient());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        logger.log(Level.INFO, ADD_DATA + period.getId());
    }

    @Override
    public List<Period> getAll() {
        List<Period> result = new ArrayList<>();
        try {
            preparedStatement = connection.prepareStatement(SELECT_ALL);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                result.add(setPeriod(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        logger.log(Level.INFO, GET_ALL_DATA + result.size());
        return result;
    }

    @Override
    public void remove(String id) {
        try {
            preparedStatement = connection.prepareStatement(DELETE + id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        logger.log(Level.INFO, REMOVE_DATA + id);
    }

    private Period setPeriod(ResultSet rs) throws SQLException {
        Period result = new Period();
        result.setId(rs.getString(ID));
        result.setWorkHours(rs.getInt(WORK_DAYS));
        result.setWorkDays(rs.getInt(WORK_HOURS));
        result.setMin(rs.getDouble(MIN));
        result.setHourlyMin(rs.getDouble(HOURLY_MIN));
        result.setMoreTimeCoefficient(rs.getDouble(MORE_TIME));
        result.setRedDayCoefficient(rs.getDouble(RED_DAY));
        result.setTaxFree(rs.getInt(TAX_FREE));
        result.setCoefficient(rs.getInt(TAX_COEFFICIENT));
        result.setBase(rs.getDouble(BASE));
        result.setPercent(rs.getDouble(TAX_PERCENT));
        result.setPnpd(rs.getDouble(PNPD));
        result.setHealthEmployee(rs.getDouble(HEALT_EE));
        result.setHealthNewEmployee(rs.getDouble(HEALT_NEE));
        result.setHealthEmployer(rs.getDouble(HEALTH_ER));
        result.setSocialEmployee(rs.getDouble(SOCIAL_EE));
        result.setSocialEmployer(rs.getDouble(SOCIAL_ER));
        result.setGuaranteeFund(rs.getDouble(GF));
        result.setSickPayCoefficient(rs.getDouble(SICK_PAY_DAY));
        result.setSickPayDay(rs.getInt(SIC_PAY_COEFFICIENT));
        return result;
    }
}
