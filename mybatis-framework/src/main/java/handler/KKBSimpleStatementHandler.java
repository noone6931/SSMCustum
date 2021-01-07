package handler;

import config.KKBMappedStatement;
import sqlsource.model.KKBBoundSql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

public class KKBSimpleStatementHandler implements KKBStatementHandler {
    @Override
    public PreparedStatement prepared(Connection connection, String sql) throws SQLException {
        return null;
    }

    @Override
    public void setParameter(Statement statement, Map<String, Object> param, KKBBoundSql boundSql) throws SQLException {

    }

    @Override
    public void query(Statement statement, KKBMappedStatement mappedStatement, List<Object> results) throws Exception {

    }
}
