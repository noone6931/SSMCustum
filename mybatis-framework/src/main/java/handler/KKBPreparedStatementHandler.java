package handler;

import config.KKBConfiguration;
import config.KKBMappedStatement;
import sqlsource.model.KKBBoundSql;

import java.sql.*;
import java.util.List;
import java.util.Map;

public class KKBPreparedStatementHandler implements KKBStatementHandler {

    private KKBParameterHandler parameterHandler;
    private KKBResultSetHandler resultSetHandler;

    public KKBPreparedStatementHandler(KKBConfiguration configuration) {
        this.parameterHandler = configuration.newKKBParameterHandler();
        this.resultSetHandler = configuration.newKKBResultSetHandler();
    }

    @Override
    public PreparedStatement prepared(Connection connection, String sql) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        return preparedStatement;
    }

    @Override
    public void setParameter(Statement statement, Map<String, Object> param, KKBBoundSql boundSql) throws SQLException {
        PreparedStatement preparedStatement = (PreparedStatement) statement;
        parameterHandler.setParameter(preparedStatement, param, boundSql);
    }

    @Override
    public void query(Statement statement, KKBMappedStatement mappedStatement, List<Object> results) throws Exception {
        PreparedStatement preparedStatement = (PreparedStatement) statement;
        ResultSet rs = preparedStatement.executeQuery();
        resultSetHandler.handleResultSet(rs, results, mappedStatement);
    }


}
