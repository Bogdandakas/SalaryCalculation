package com.vcs.bogdan.service.db;

import java.io.*;
import java.util.Properties;

import static com.vcs.bogdan.service.db.ConnectionData.*;

class PropertiesFactory {

    private static final String EQUALS = "\\u003D";
    private static final int KEY = 0;
    private static final int VALUE = 1;

    boolean isRemoteHostType() {
        return getDefaultHostType().equals(REMOTE);
    }

    String getDefaultHostType() {
        String result = "";

        Properties properties = new Properties();
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        InputStream stream = loader.getResourceAsStream(APP_PROPERTIES);
        try {
            properties.load(stream);
            result = properties.getProperty(HOST_TYPE);
        } catch (IOException e) {
            e.getMessage();
        }
        return result;
    }

    ConnectionData get(String hostType) {
        return hostType.equals(REMOTE) ? getDataFromFile() : getDataFromProperties();
    }

    private ConnectionData getDataFromFile() {
        ConnectionData result = new ConnectionData();

        try {
            BufferedReader reader = new BufferedReader(new FileReader(new File(DB_PROPERTIES)));

            String line;
            while ((line = reader.readLine()) != null) {
                String[] array = line.split(EQUALS);
                if (array[KEY].equals(URL)) {
                    result.setUrl(array[VALUE]);
                }
                if (array[KEY].equals(USER)) {
                    result.setUser(array[VALUE]);
                }
                if (array[KEY].equals(PASSWORD)) {
                    result.setPassword(array[VALUE]);
                }
            }
            reader.close();

        } catch (IOException e) {
            e.getMessage();
        }
        return result;
    }

    private ConnectionData getDataFromProperties() {
        ConnectionData result = new ConnectionData();

        Properties properties = new Properties();
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        InputStream stream = loader.getResourceAsStream(APP_PROPERTIES);

        try {
            properties.load(stream);
            result.setUrl(properties.getProperty(URL));
            result.setUser(properties.getProperty(USER));
            result.setPassword(properties.getProperty(PASSWORD));
        } catch (IOException e) {
            e.getMessage();
        }
        return result;
    }


    String getProp(String key) {

        Properties properties = new Properties();
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        InputStream stream = loader.getResourceAsStream(APP_PROPERTIES);
        try {
            properties.load(stream);
            return properties.getProperty(key, null);
        } catch (IOException e) {
            e.getMessage();
        }
        return null;
    }
}