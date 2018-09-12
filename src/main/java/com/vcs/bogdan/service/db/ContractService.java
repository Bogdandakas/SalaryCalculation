package com.vcs.bogdan.service.db;

import com.mysql.jdbc.StringUtils;
import com.vcs.bogdan.beans.Contract;
import com.vcs.bogdan.beans.LogHandler;
import com.vcs.bogdan.service.ContractComparator;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.vcs.bogdan.beans.Contract.*;
import static com.vcs.bogdan.service.db.ConnectionServiceImpl.connection;
import static com.vcs.bogdan.service.db.ConnectionServiceImpl.preparedStatement;
import static com.vcs.bogdan.beans.LogHandler.*;

public class ContractService implements DBService<Contract> {

    private static final String INSERT = "INSERT INTO " + TABLE + " Values(?,?,?,?,?,?,?,?)";
    private static final String Q = " =?, ";
    private static final String Q_LAST = " =?";
    private static final String WHERE_ID = " WHERE id =";
    private static final String UPDATE = "UPDATE " + TABLE + " SET " + ID + Q + PERSON_ID + Q + DATE +
            Q + EVENT + Q + TYPE + Q + DAY_HOURS + Q + WAGE + Q + IS_MAIN + Q_LAST + WHERE_ID;
    private static final String SELECT_ALL = "SELECT * FROM " + TABLE;
    private static final String SELECT = SELECT_ALL + WHERE_ID;
    private static final String DELETE = "DELETE FROM " + TABLE + WHERE_ID;
    private static final int NUMBER = 100; //deduct coefficient

    Logger logger = Logger.getLogger(LogHandler.class.getName());

    @Override
    public Contract get(String id) {

        Contract result = new Contract();
        try {
            preparedStatement = connection.prepareStatement(SELECT + id);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                result = setContract(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        logger.log(Level.INFO, GET_DATA + result.getId());
        return result;
    }

    @Override
    public void add(Contract contract) {
        String id = getId(contract);
        String queryStatement = (StringUtils.isNullOrEmpty(get(id).getId()) ? INSERT : UPDATE + id);

        try {
            int index = 1;
            preparedStatement = connection.prepareStatement(queryStatement);
            preparedStatement.setObject(index++, id);
            preparedStatement.setObject(index++, contract.getPersonId());
            preparedStatement.setObject(index++, contract.getDate());
            preparedStatement.setObject(index++, contract.getEvent().toString());
            preparedStatement.setObject(index++, contract.getType().toString());
            preparedStatement.setObject(index++, contract.getDayHours());
            preparedStatement.setObject(index++, contract.getWage());
            preparedStatement.setObject(index, contract.isMain());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        logger.log(Level.INFO, ADD_DATA + contract.getId());
    }

    @Override
    public List<Contract> getAll() {
        List<Contract> result = new ArrayList<>();

        try {
            preparedStatement = connection.prepareStatement(SELECT_ALL);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                result.add(setContract(rs));
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

    public Contract getValidateContract(String periodId, String personId) {
        Contract result = new Contract();

        List<Contract> contracts = new ContractService().getAll();
        contracts.sort(new ContractComparator());

        for (Contract contract : contracts) {
            boolean isInclusiveDate = contract.getDate() / NUMBER <= Integer.valueOf(periodId);
            boolean isPerson = contract.getPersonId().equals(personId);
            if (isInclusiveDate && isPerson) {
                result = contract;
            }
        }
        return result;
    }

    private Contract setContract(ResultSet rs) throws SQLException {
        Contract result = new Contract();
        result.setId(rs.getString(ID));
        result.setPersonId(rs.getString(PERSON_ID));
        result.setDate(rs.getLong(DATE));
        result.setEvent(rs.getString(EVENT));
        result.setType(rs.getString(TYPE));
        result.setDayHours(rs.getInt(DAY_HOURS));
        result.setWage(rs.getDouble(WAGE));
        result.setMain(rs.getBoolean(IS_MAIN));
        return result;
    }

    private String getId(Contract contract) {

        if (StringUtils.isNullOrEmpty(contract.getId())) {
            for (Contract c : getAll()) {
                if (isContractByPersonIdAndDate(c, contract.getPersonId(), contract.getDate())) {
                    return c.getId();
                }
            }
        } else {
            return contract.getId();
        }
        return null;
    }

    private boolean isContractByPersonIdAndDate(Contract contract, String personId, long date) {
        return contract.getPersonId().equals(personId) && contract.getDate() == date;
    }

}