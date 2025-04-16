package com.betsesportivas.Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Db {

    private final String _url;
    private final String _username;
    private final String _password;

    public Db(String url, String username, String password) {
        this._url = url;
        this._username = username;
        this._password = password;
    }

    public Connection Connect() throws SQLException {
        return (DriverManager.getConnection(this._url, this._username, this._password));
    }
}
