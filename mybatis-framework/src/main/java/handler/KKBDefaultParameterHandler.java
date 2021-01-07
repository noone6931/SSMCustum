package handler;

import sqlsource.model.KKBBoundSql;
import sqlsource.model.KKBParameterMapping;
import utils.KKBSimpleTypeRegistry;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

public class KKBDefaultParameterHandler implements KKBParameterHandler {
    @Override
    public void setParameter(Statement statement, Map<String, Object> param, KKBBoundSql boundSql) throws SQLException {
        if (statement instanceof PreparedStatement) {
            PreparedStatement preparedStatement = (PreparedStatement) statement;
            if (KKBSimpleTypeRegistry.isSimpleType(param.getClass())) {
                preparedStatement.setObject(1, param);
            } else if (param instanceof Map) {
                Map paramMap = (Map) param;
                List<KKBParameterMapping> parameterMappings = boundSql.getParameterMappings();
                for (int i = 0; i < parameterMappings.size(); i++) {
                    KKBParameterMapping parameterMapping = parameterMappings.get(i);
                    String name = parameterMapping.getName();
                    // 获取到的参数
                    Object value = paramMap.get(name);
                    // 获取到的value对应的Java类型
                    Class type = parameterMapping.getType();
                    if (type != null) {
                        // TODO
                    } else {
                        preparedStatement.setObject(i + 1, value);
                    }
                }
            } else {
                // TODO
            }

        }
    }
}
