package com.qiren.project.connection;

import com.qiren.project.exceptions.MysqlConnectionException;
import com.qiren.project.exceptions.MysqlDriverNotFoundException;
import com.qiren.project.util.Constants;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
    // private static Connection connection;

    public synchronized static Connection getConnection()
            throws MysqlDriverNotFoundException, MysqlConnectionException {
        // it is said not to use singleton with mysql connection
        // https://stackoverflow.com/questions/814206/getting-db-connection-through-singleton-class
        return createConnection();
    }

    private static Connection createConnection()
            throws MysqlDriverNotFoundException, MysqlConnectionException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(Constants.DATABASE_URL,
                    Constants.DATABASE_USERNAME, Constants.DATABASE_PASSWORD);
        } catch (ClassNotFoundException e) {
            throw new MysqlDriverNotFoundException(e.getMessage());
        } catch (SQLException e) {
            throw new MysqlConnectionException(e.getMessage());
        }
    }
}
