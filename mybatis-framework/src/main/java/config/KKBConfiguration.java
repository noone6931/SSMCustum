package config;

import executor.KKBCachingExecutor;
import executor.KKBExecutor;
import executor.KKBSimpleExecutor;
import handler.*;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

public class KKBConfiguration {
    private DataSource dataSource;
    private boolean useCache = true;
    private Map<String, KKBMappedStatement> mappedStatementMap = new HashMap<>();

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public KKBMappedStatement getMappedStatementById(String statementId) {
        return mappedStatementMap.get(statementId);
    }

    public void addMappedStatementMap(String statementId, KKBMappedStatement KKBMappedStatement) {
        mappedStatementMap.put(statementId, KKBMappedStatement);
    }

    public KKBExecutor newKKBExecutor(String executorType) {
        executorType = executorType == null || executorType.trim().equals("") ? "simple" : executorType;
        KKBExecutor executor = null;
        if (executorType.equals("simple")) {
            executor = new KKBSimpleExecutor();
        }
        // 默认使用二级缓存执行器去执行一遍
        if (useCache){
            return new KKBCachingExecutor(executor);
        }
        return executor;
    }

    public KKBParameterHandler newKKBParameterHandler() {
        return new KKBDefaultParameterHandler();
    }

    public KKBResultSetHandler newKKBResultSetHandler() {
        return new KKBDefaultResultSetHandler();
    }

    public KKBStatementHandler newKKBStatementHandler(String statementType) {
        if ("prepared".equals(statementType)) {
            return new KKBPreparedStatementHandler(this);
        } else if ("callable".equals(statementType)) {
            return new KKBCallableStatementHandler();
        } else {
            return new KKBSimpleStatementHandler();
        }
    }
}
