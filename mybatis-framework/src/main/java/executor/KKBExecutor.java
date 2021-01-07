package executor;

import config.KKBConfiguration;
import config.KKBMappedStatement;

import java.util.List;
import java.util.Map;

public interface KKBExecutor {
    <T> List<T> query(KKBConfiguration configuration, KKBMappedStatement mappedStatement, Map<String, Object> param);
}
