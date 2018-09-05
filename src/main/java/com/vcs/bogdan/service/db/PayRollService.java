package com.vcs.bogdan.service.db;

import com.mysql.jdbc.StringUtils;
import com.vcs.bogdan.beans.LogHandler;
import com.vcs.bogdan.beans.PayRoll;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.vcs.bogdan.beans.PayRoll.*;
import static com.vcs.bogdan.service.db.ConnectionServiceImpl.connection;
import static com.vcs.bogdan.service.db.ConnectionServiceImpl.preparedStatement;
import static com.vcs.bogdan.beans.LogHandler.*;

public class PayRollService implements DBService<PayRoll> {

    private static final String INSERT = "INSERT INTO " + TABLE + " Values(?,?,?,?,?,?,?)";
    private static final String Q = " = ?, ";
    private static final String Q_LAST = " = ?";
    private static final String WHERE_ID = " WHERE id = ";
    private static final String UPDATE = "UPDATE " + TABLE + " SET " +
            ID + Q + PERIOD_ID + Q + PERSON_ID + Q + INCOME + Q + TAX + Q + INSURANCE + Q + OUTCOME + Q_LAST + WHERE_ID;
    private static final String SELECT_ALL = "SELECT * FROM " + TABLE;
    private static final String SELECT = SELECT_ALL + WHERE_ID;
    private static final String DELETE = "DELETE FROM " + TABLE + WHERE_ID;

    Logger logger = Logger.getLogger(LogHandler.class.getName());

    @Override
    public PayRoll get(String id) {
        PayRoll result = new PayRoll();
        try {
            int index = 1;
            preparedStatement = connection.prepareStatement(SELECT + id);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) result = setPayRoll(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        logger.log(Level.INFO, GET_DATA + result.getId());
        return result;
    }

    @Override
    public void add(PayRoll payRoll) {
        String id = getId(payRoll);
        String queryStatement = (StringUtils.isNullOrEmpty(get(id).getId()) ? INSERT : UPDATE + id);
        try {
            int index = 1;
            preparedStatement = connection.prepareStatement(queryStatement);
            preparedStatement.setObject(index++, id);
            preparedStatement.setObject(index++, payRoll.getPeriodId());
            preparedStatement.setObject(index++, payRoll.getPersonId());
            preparedStatement.setObject(index++, payRoll.getIncome());
            preparedStatement.setObject(index++, payRoll.getTax());
            preparedStatement.setObject(index++, payRoll.getInsurance());
            preparedStatement.setObject(index, payRoll.getOutcome());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        logger.log(Level.INFO, ADD_DATA + payRoll.getId());
    }

    @Override
    public List<PayRoll> getAll() {
        List<PayRoll> result = new ArrayList<>();
        try {
            preparedStatement = connection.prepareStatement(SELECT_ALL);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) result.add(setPayRoll(rs));
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

    private PayRoll setPayRoll(ResultSet rs) throws SQLException {
        PayRoll result = new PayRoll();
        result.setId(rs.getString(ID));
        result.setPeriodId(rs.getString(PERIOD_ID));
        result.setPersonId(rs.getString(PERSON_ID));
        result.setIncome(rs.getDouble(INCOME));
        result.setTax(rs.getDouble(TAX));
        result.setInsurance(rs.getDouble(INSURANCE));
        result.setOutcome(rs.getDouble(OUTCOME));
        return result;
    }

    private String getId(PayRoll payRoll) {

        if (StringUtils.isNullOrEmpty(payRoll.getId())) {
            for (PayRoll p : getAll()) {
                boolean isPeriod = p.getPeriodId().equals(payRoll.getPeriodId());
                boolean isPerson = p.getPersonId().equals(payRoll.getPersonId());
                if (isPeriod && isPerson) {
                    return p.getId();
                }
            }
        } else {
            return payRoll.getId();
        }
        return null;
    }
}

