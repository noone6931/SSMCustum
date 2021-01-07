package handler;

import config.KKBMappedStatement;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.List;

public class KKBDefaultResultSetHandler implements KKBResultSetHandler {

    @Override
    public <T> void handleResultSet(ResultSet resultSet, List<T> results, KKBMappedStatement mappedStatement) throws Exception {
        // 遍历查询结果集
        Class clazz = mappedStatement.getResultClass();
        Object result = null;
        while (resultSet.next()) {
            result = clazz.newInstance();
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();
            for (int i = 1; i <= columnCount; i++) {
                // 取出resultset中的每一行结果中的列的名称
                String columnName = metaData.getColumnName(i);
                // 要求列的名称和映射 对象的属性名称要一致
                Field field = clazz.getDeclaredField(columnName);
                // 暴力访问私有成员
                field.setAccessible(true);
                field.set(result, resultSet.getObject(i));
            }
            results.add((T) result);
        }
    }
}
