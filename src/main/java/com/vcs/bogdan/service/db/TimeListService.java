package com.vcs.bogdan.service.db;

import com.mysql.jdbc.StringUtils;
import com.vcs.bogdan.beans.LogHandler;
import com.vcs.bogdan.beans.TimeList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.vcs.bogdan.beans.LogHandler.*;
import static com.vcs.bogdan.beans.TimeList.*;
import static com.vcs.bogdan.service.db.ConnectionServiceImpl.connection;
import static com.vcs.bogdan.service.db.ConnectionServiceImpl.preparedStatement;

public class TimeListService implements DBService<TimeList> {

    private static final String INSERT = "INSERT INTO " + TABLE + " Values(?,?,?,?,?)";
    private static final String Q = " =?, ";
    private static final String Q_LAST = " =?";
    private static final String WHERE_ID = " WHERE id =";
    private static final String UPDATE = "UPDATE " + TABLE + " SET " + ID + Q + DATE + Q + PERSON_ID + Q + EVENT + Q + VALUE + Q_LAST + WHERE_ID;
    private static final String SELECT_ALL = "SELECT * FROM " + TABLE;
    private static final String SELECT = SELECT_ALL + WHERE_ID;
    private static final String DELETE = "DELETE FROM " + TABLE + WHERE_ID;
    private static final int NUMBER = 100; //deduct coefficient

    Logger logger = Logger.getLogger(LogHandler.class.getName());

    @Override
    public TimeList get(String id) {
        TimeList result = new TimeList();
        try {
            preparedStatement = connection.prepareStatement(SELECT + id);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                result = setTimeList(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        logger.log(Level.INFO, GET_DATA + result.getId());
        return result;
    }

    @Override
    public void add(TimeList timeList) {
        String id = getId(timeList);
        String queryStatement = (StringUtils.isNullOrEmpty(get(id).getId()) ? INSERT : UPDATE + id);
        try {
            int index = 1;
            preparedStatement = connection.prepareStatement(queryStatement);
            preparedStatement.setObject(index++, id);
            preparedStatement.setObject(index++, timeList.getDate());
            preparedStatement.setObject(index++, timeList.getPersonId());
            preparedStatement.setObject(index++, timeList.getEvent().toString());
            preparedStatement.setObject(index, timeList.getValue());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        logger.log(Level.INFO, ADD_DATA + timeList.getId());
    }

    @Override
    public List<TimeList> getAll() {
        List<TimeList> result = new ArrayList<>();
        try {
            preparedStatement = connection.prepareStatement(SELECT_ALL);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                result.add(setTimeList(rs));
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

    public double getHours(List<TimeList> timeLists, String personId, String periodId) {
        double result = 0;
        for (TimeList t : timeLists) {
            boolean a = isPersonIdEqualsID(t.getPersonId(), personId);
            boolean b = isInclusiveDateInPeriod(t.getDate(), periodId);
            result = a && b ? result + t.getValue() : result;
        }
        logger.log(Level.INFO, GET_HOURS + result);
        return result;
    }

    public double getDays(List<TimeList> timeLists, String personId, String periodId) {
        double result = 0;
        for (TimeList t : timeLists) {
            boolean a = isPersonIdEqualsID(t.getPersonId(), personId);
            boolean b = isInclusiveDateInPeriod(t.getDate(), periodId);
            if (a && b) {
                result++;
            }
        }
        logger.log(Level.INFO, GET_DAYS + result);
        return result;
    }

    private boolean isPersonIdEqualsID(String peronId, String id) {
        return peronId.equals(id);
    }

    private boolean isInclusiveDateInPeriod(long date, String periodId) {
        return date / NUMBER == Integer.valueOf(periodId);
    }

    private String getId(TimeList timeList) {

        if (StringUtils.isNullOrEmpty(timeList.getId())) {
            for (TimeList p : getAll()) {
                boolean isPersonId = p.getPersonId().equals(timeList.getPersonId());
                boolean isEvent = p.getEvent().equals(timeList.getEvent());
                boolean isDate = p.getDate() == timeList.getDate();
                if (isPersonId && isEvent && isDate) {
                    return p.getId();
                }
            }
        } else {
            return timeList.getId();
        }
        return null;
    }

    private TimeList setTimeList(ResultSet rs) throws SQLException {
        TimeList result = new TimeList();
        result.setId(rs.getString(ID));
        result.setDate(rs.getLong(DATE));
        result.setPersonId(rs.getString(PERSON_ID));
        result.setEvent(rs.getString(EVENT));
        result.setValue(rs.getDouble(VALUE));
        return result;
    }

}

