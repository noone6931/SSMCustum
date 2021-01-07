package handler;

import config.KKBMappedStatement;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

public interface KKBResultSetHandler {

    <T> void handleResultSet(ResultSet resultSet, List<T> results, KKBMappedStatement mappedStatement) throws Exception;
}
