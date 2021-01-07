package sqlsession;

import config.KKBConfiguration;
import config.KKBMappedStatement;
import executor.KKBExecutor;

import java.util.List;
import java.util.Map;

public class KKBDefaultSqlSession implements KKBSqlSession {

    private KKBConfiguration configuration;

    public KKBDefaultSqlSession(KKBConfiguration configuration) {
        this.configuration = configuration;
    }

    @Override
    public <T> List<T> selectList(String statementId, Map<String, Object> param) {
        KKBMappedStatement mappedStatement = configuration.getMappedStatementById(statementId);
        KKBExecutor executor = configuration.newKKBExecutor("");
        List<Object> list = executor.query(configuration,mappedStatement, param);
        return (List<T>) list;
    }

    @Override
    public <T> T selectOne(String s, Map<String, Object> param) {
        List<Object> list = this.selectList(s, param);
        if (list == null) {
            return null;
        }
        if (list.size() == 1) {
            return (T) list.get(0);
        }
        if (list.size() != 1) {
            // TODO throw
        }
        return null;
    }
}
