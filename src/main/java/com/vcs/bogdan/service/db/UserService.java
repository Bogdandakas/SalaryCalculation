package com.vcs.bogdan.service.db;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserService {

    public void start() {
        ConnectionServiceImpl dbConnection = new ConnectionServiceImpl();
        dbConnection.connect();
    }

    public void close() {
        try {
            ConnectionServiceImpl.connection.close();
            PreparedStatement statement = ConnectionServiceImpl.preparedStatement;
            if (statement != null) {
                statement.close();
            }

        } catch (SQLException e) {
            e.getMessage();
        }
    }
}
