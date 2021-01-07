package executor;

import config.KKBConfiguration;
import config.KKBMappedStatement;
import sqlsource.iface.KKBSqlSource;
import sqlsource.model.KKBBoundSql;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 一级缓存
 */
public abstract class KKBBaseExecutor implements KKBExecutor {

    public static Map<String, List<Object>> levelOneCache = new HashMap<>();

    @Override
    public <T> List<T> query(KKBConfiguration configuration, KKBMappedStatement mappedStatement, Map<String, Object> param) {
        KKBSqlSource sqlSource = mappedStatement.getSqlSource();
        KKBBoundSql boundSql = sqlSource.getBoundSql(param);
        String sql = boundSql.getSql();
        List<Object> list = levelOneCache.get(sql);
        if (list != null && list.size() > 0) {
            return (List<T>) list;
        }
        list = queryFromDB(configuration, mappedStatement, param);
        levelOneCache.put(sql, list);
        return (List<T>) list;
    }

    public abstract <T> List<T> queryFromDB(KKBConfiguration configuration, KKBMappedStatement mappedStatement, Map<String, Object> param);
}