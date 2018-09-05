package com.vcs.bogdan.service.db;

class ConnectionData {

    public static final String APP_PROPERTIES = ".//app.properties";
    public static final String DB_PROPERTIES = "src/main/resources/dbProperties.txt";
    public static final String LOCAL = "local";
    public static final String REMOTE = "remote";
    public static final String HOST_TYPE = "hostType";
    public static final String URL = "url";
    public static final String USER = "user";
    public static final String PASSWORD = "password";
    public static final String DRIVER = "dbDriver";

    private String url;
    private String user;
    private String password;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
