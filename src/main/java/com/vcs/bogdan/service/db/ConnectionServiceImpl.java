package com.vcs.bogdan.service.db;

import com.vcs.bogdan.beans.LogHandler;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.vcs.bogdan.service.db.ConnectionData.DRIVER;
import static com.vcs.bogdan.service.db.ConnectionData.LOCAL;
import static com.vcs.bogdan.beans.LogHandler.*;

public class ConnectionServiceImpl implements ConnectionService {

    static java.sql.Connection connection = null;
    static PreparedStatement preparedStatement = null;

    Logger logger = Logger.getLogger(LogHandler.class.getName());

    @Override
    public boolean isDefaultConnection() {
        String hostType = new PropertiesFactory().getDefaultHostType();
        ConnectionData connData = new PropertiesFactory().get(hostType);

        if (connData.getUrl() != null) {
            try {
                logger.log(Level.INFO, CONNECT_TO + connData.getUrl());
                connection = DriverManager.getConnection(connData.getUrl(), connData.getUser(), connData.getPassword());
                return connection != null;

            } catch (SQLException e) {
                logger.log(Level.WARNING, CONNECTION_FAILED + e.getMessage());
                return false;
            }
        } else {
            return false;
        }
    }

    @Override
    public void connect() {
        PropertiesFactory factory = new PropertiesFactory();

        try {
            Class.forName(factory.getProp(DRIVER));
        } catch (ClassNotFoundException e) {
            logger.log(Level.WARNING, NOT_FIND_DRIVER + e.getMessage());
            return;
        }

        boolean isDefaultConnection = isDefaultConnection();
        boolean isHostTypeRemote = factory.isRemoteHostType();
        try {
            if (!isDefaultConnection && isHostTypeRemote) {

                ConnectionData connectionData = factory.get(LOCAL);

                logger.log(Level.INFO, CONNECT_TO + connectionData.getUrl());
                connection = DriverManager.getConnection(connectionData.getUrl(), connectionData.getUser(), connectionData.getPassword());
            }
            logger.log(Level.INFO, CONNECTION_SUCCESSFUL);

        } catch (SQLException e) {
            logger.log(Level.WARNING, CONNECTION_FAILED);
            e.printStackTrace();
        }
    }
}
