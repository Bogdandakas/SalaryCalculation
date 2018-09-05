package com.vcs.bogdan.service.db;

import com.mysql.jdbc.StringUtils;
import com.vcs.bogdan.beans.LogHandler;
import com.vcs.bogdan.beans.Person;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.vcs.bogdan.beans.Person.*;
import static com.vcs.bogdan.service.db.ConnectionServiceImpl.connection;
import static com.vcs.bogdan.service.db.ConnectionServiceImpl.preparedStatement;
import static com.vcs.bogdan.beans.LogHandler.*;

public class PersonService implements DBService<Person> {

    private static final String INSERT = "INSERT INTO " + TABLE + " Values(?,?,?)";
    private static final String Q = " =?, ";
    private static final String Q_LAST = " =?";
    private static final String WHERE_ID = " WHERE id =";
    private static final String UPDATE = "UPDATE " + TABLE + " SET " + ID + Q + NAME + Q + SURNAME + Q_LAST + WHERE_ID;
    private static final String SELECT_ALL = "SELECT * FROM " + TABLE;
    private static final String SELECT = SELECT_ALL + WHERE_ID;
    private static final String DELETE = "DELETE FROM " + TABLE + WHERE_ID;

    Logger logger = Logger.getLogger(LogHandler.class.getName());

    @Override
    public Person get(String id) {
        Person result = new Person();

        try {
            preparedStatement = connection.prepareStatement(SELECT + id);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                result = setPerson(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        logger.log(Level.INFO, GET_DATA + result.getId());
        return result;
    }

    @Override
    public void add(Person person) {
        String id = person.getId();
        String queryStatement = (StringUtils.isNullOrEmpty(get(id).getId()) ? INSERT : UPDATE + id);

        try {
            int index = 1;
            preparedStatement = connection.prepareStatement(queryStatement);
            preparedStatement.setObject(index++, id);
            preparedStatement.setObject(index++, person.getName());
            preparedStatement.setObject(index, person.getSurname());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        logger.log(Level.INFO, ADD_DATA + person.getId());
    }

    @Override
    public List<Person> getAll() {
        List<Person> result = new ArrayList<>();

        try {
            preparedStatement = connection.prepareStatement(SELECT_ALL);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) result.add(setPerson(rs));
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

    private Person setPerson(ResultSet rs) throws SQLException {
        Person result = new Person();
        result.setId(rs.getString(ID));
        result.setName(rs.getString(NAME));
        result.setSurname(rs.getString(SURNAME));
        return result;
    }
}
