package com.lightcone.debuger.common;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by huang_xiao_xian
 * Date : 2018/12/25.
 */
public class ResultSetCustom {

    private ResultSet resultSet;

    public Boolean isExistColumn(String column) {
        try {
            if (resultSet.findColumn(column) > 0) {
                return true;
            }
        } catch (SQLException e) {
            return false;
        }
        return false;
    }

    public ResultSetCustom() {
    }

    public ResultSetCustom(ResultSet resultSet) {
        this.resultSet = resultSet;
    }

    public ResultSet getResultSet() {
        return resultSet;
    }

    public void setResultSet(ResultSet resultSet) {
        this.resultSet = resultSet;
    }
}
