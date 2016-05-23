package com.test.dbroute;

/**
 * Created by tanzepeng on 2015/6/29.
 */
public class DBRouteStrategy {
    private String dbKey;

    public DBRouteStrategy(String dbKey) {
        this.dbKey = dbKey;
    }

    public String getDbKey() {
        return dbKey;
    }

    public void setDbKey(String dbKey) {
        this.dbKey = dbKey;
    }
}
