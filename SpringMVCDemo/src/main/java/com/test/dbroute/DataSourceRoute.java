package com.test.dbroute;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by tanzepeng on 2015/6/26.
 */
public class DataSourceRoute implements DataSource {

    private static Logger log = LoggerFactory.getLogger(DataSourceRoute.class);
    /*默认数据源*/
    private static final String DEFAULT_DATASOURCE_ROUTE = "zhejiang";
    /*使用Key-Value映射的DataSource*/
    private Map<String, DataSource> targetDataSources;

    public Map<String, DataSource> getTargetDataSources() {
        return targetDataSources;
    }

    public DataSourceRoute() {
        targetDataSources = new HashMap<String, DataSource>(4);
    }

    public DataSource getCurrentDataSource() {
        String dbKey = DBRouteContext.getName();
        if (dbKey == null) {
            /*throw new IllegalStateException("No Mapped DataSources Exist!");*/
            dbKey = DEFAULT_DATASOURCE_ROUTE;
        }

        DataSource dataSource = targetDataSources.get(dbKey);
        if (dataSource == null) {
            throw new IllegalStateException("No Mapped DataSources Exist!");
        }
        return dataSource;
    }

    public void setTargetDataSources(Map<String, DataSource> targetDataSources) {
        this.targetDataSources = targetDataSources;
    }

    public Connection getConnection() throws SQLException {
        return getCurrentDataSource().getConnection();
    }

    public Connection getConnection(String username, String password) throws SQLException {
        return getCurrentDataSource().getConnection(username, password);
    }

    public PrintWriter getLogWriter() throws SQLException {
        return null;
    }

    public void setLogWriter(PrintWriter out) throws SQLException {

    }

    public void setLoginTimeout(int seconds) throws SQLException {

    }

    public int getLoginTimeout() throws SQLException {
        return 0;
    }

    public <T> T unwrap(Class<T> iface) throws SQLException {
        return null;
    }

    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return false;
    }

    public java.util.logging.Logger getParentLogger() throws SQLFeatureNotSupportedException {
        return null;
    }
}
