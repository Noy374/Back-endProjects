package com.svarian.regusers;

import com.mysql.jdbc.Driver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class JDBC {
    public static Connection connectionToDB() {
        String jdbcUrl = "jdbc:mysql://localhost:3306/mydb";
        String username = "root";
        String password = "root";
        Connection connection;
        Driver driver = null;
        try {
            driver = new Driver();
            DriverManager.registerDriver(driver);
            connection = DriverManager.getConnection(jdbcUrl, username, password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return connection;

    }
}
