package handler;

import sqlsource.model.KKBBoundSql;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

public interface KKBParameterHandler {
    void setParameter(Statement statement, Map<String, Object> param, KKBBoundSql boundSql) throws SQLException;


}
