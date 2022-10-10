package com.qiren.project.dao;

import com.qiren.project.connection.ConnectionFactory;
import com.qiren.project.exceptions.MysqlConnectionException;
import com.qiren.project.exceptions.MysqlDriverNotFoundException;
import com.qiren.project.exceptions.SQLExecutionException;
import com.qiren.project.util.LoggingCenter;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.SQLException;

public abstract class AbstractDAO {

    private Connection connection;

    /**
     * Get Database Connection.
     *
     * @throws MysqlConnectionException     Error When Connected to Database.
     * @throws MysqlDriverNotFoundException Driver Not Found.
     */
    protected void beginTransaction()
            throws MysqlConnectionException, MysqlDriverNotFoundException {
        connection = ConnectionFactory.getConnection();
    }

    /**
     * Auto Build Insert Statement
     *
     * @see #simpleInsert(Object...)
     */
    private String getInsertStatement(Object... params) {
        StringBuilder sql = new StringBuilder("insert into " + getTableName() + " values (");
        for (int i = 0; i < params.length; i++) {
            if (i != 0) {
                sql.append(",");
            }
            sql.append("?");
        }
        return sql.append(");").toString();
    }

    /**
     * Build Insert SQL statement automatically and execute.
     *
     * @param params Values to be inserted
     * @throws SQLExecutionException Potential SQL error when executing.
     */
    protected void simpleInsert(Object... params) throws SQLExecutionException {
        insert(getInsertStatement(params), params);
    }

    protected void insert(String sql, Object... params) throws SQLExecutionException {
        QueryRunner runner = new QueryRunner();
        try {
            LoggingCenter.info("Prepare to insert with SQL: " + sql);
            int affected = runner.update(connection, sql, params);
            LoggingCenter.info(affected + " lines affected.");
        } catch (SQLException e) {
            throw new SQLExecutionException(e.getMessage());
        }
    }

    private String getSelectStatement(String key, String value) {
        return "select * from " + getTableName() + " where (" + key + " = '" + value + "');";
    }

    protected Object simpleQueryList(String key, String value, Class resultType) {
        return queryList(getSelectStatement(key, value), resultType);
    }

    protected <T> T simpleQuery(String key, String value, Class<T> resultType) {
        return query(getSelectStatement(key, value), resultType);
    }

    protected Object queryList(String sql, Class resultType) throws SQLExecutionException {
        QueryRunner runner = new QueryRunner();
        try {
            LoggingCenter.info("Prepare to select with SQL: " + sql);
            Object o = runner.query(connection, sql, new BeanListHandler<>(resultType));
            return o;
        } catch (SQLException e) {
            throw new SQLExecutionException(e.getMessage());
        }
    }

    protected <T> T query(String sql, Class<T> resultType) throws SQLExecutionException {
        QueryRunner runner = new QueryRunner();
        try {
            LoggingCenter.info("Prepare to select with SQL: " + sql);
            Object o = runner.query(connection, sql, new BeanHandler<>(resultType));
            return (T) o;
        } catch (SQLException e) {
            throw new SQLExecutionException(e.getMessage());
        }
    }

    protected long queryCount(String sql) throws SQLExecutionException {
        QueryRunner runner = new QueryRunner();
        ScalarHandler<Long> scalarHandler = new ScalarHandler<>();
        try {
            LoggingCenter.info("Prepare to select with SQL: " + sql);
            return runner.query(connection, sql, scalarHandler);
        } catch (SQLException e) {
            throw new SQLExecutionException(e.getMessage());
        }
    }

    protected <T> T query(String sql, Class<T> resultType, Object... params) throws SQLExecutionException {
        QueryRunner runner = new QueryRunner();
        try {
            LoggingCenter.info("Prepare to select with SQL: " + sql);
            Object o = runner.query(connection, sql, new BeanHandler<>(resultType), params);
            return (T) o;
        } catch (SQLException e) {
            throw new SQLExecutionException(e.getMessage());
        }
    }

    protected Object queryList(String sql, Class resultType, Object... params) throws SQLExecutionException {
        QueryRunner runner = new QueryRunner();
        try {
            LoggingCenter.info("Prepare to select with SQL: " + sql);
            Object o = runner.query(connection, sql, new BeanListHandler<>(resultType), params);
            return o;
        } catch (SQLException e) {
            throw new SQLExecutionException(e.getMessage());
        }
    }

    protected void update(String sql, Object... params) throws SQLExecutionException {
        QueryRunner runner = new QueryRunner();
        try {
            LoggingCenter.info("Prepare to update with SQL: " + sql);
            int affected = runner.update(connection, sql, params);
            LoggingCenter.info(affected + " lines affected.");
        } catch (SQLException e) {
            throw new SQLExecutionException(e.getMessage());
        }
    }

    private String getDeleteStatement(String deleteKey, String value) {
        return "delete from " + getTableName() + " where (" + deleteKey + " = '" + value + "')";
    }

    protected void simpleDelete(String key, String value) {
        delete(getDeleteStatement(key, value));
    }

    protected void delete(String sql) throws SQLExecutionException {
        QueryRunner runner = new QueryRunner();
        try {
            LoggingCenter.info("Prepare to delete with SQL: " + sql);
            int affected = runner.update(connection, sql);
            LoggingCenter.info(affected + " lines affected.");
        } catch (SQLException e) {
            throw new SQLExecutionException(e.getMessage());
        }
    }

    /**
     * Close Database Connection.
     */
    protected void endTransaction() {
        DbUtils.closeQuietly(connection);
    }

    /**
     * Get Current Table Name.
     *
     * @return table name.
     */
    protected abstract String getTableName();
}
