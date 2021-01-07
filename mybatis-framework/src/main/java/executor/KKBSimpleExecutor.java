package executor;

import config.KKBConfiguration;
import config.KKBMappedStatement;
import handler.KKBStatementHandler;
import sqlsource.model.KKBBoundSql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class KKBSimpleExecutor extends KKBBaseExecutor {

    @Override
    public <T> List<T> queryFromDB(KKBConfiguration configuration, KKBMappedStatement mappedStatement, Map<String, Object> param) {

        List<Object> results = new ArrayList<>();
        Connection connection = null;

        ResultSet resultSet = null;
        try {
            connection = configuration.getDataSource().getConnection();
            KKBBoundSql boundSql = mappedStatement.getSqlSource().getBoundSql(param);
            String sql = boundSql.getSql();
            // 3.创建statement对象
//            statement = createStatement(connection, sql, mappedStatement);
            KKBStatementHandler statementHandler = configuration.newKKBStatementHandler("prepared");
            // 4.设置参数
            PreparedStatement statement = statementHandler.prepared(connection, sql);
            statementHandler.setParameter(statement, param, boundSql);
            statementHandler.query(statement, mappedStatement, results);
            // 6.处理ResultSet
            return (List<T>) results;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 释放资源
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}
