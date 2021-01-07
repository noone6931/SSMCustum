package executor;

import config.KKBConfiguration;
import config.KKBMappedStatement;

import java.util.List;
import java.util.Map;

public class KKBCachingExecutor  implements KKBExecutor{

    private KKBExecutor executor;

    public KKBCachingExecutor(KKBExecutor executor) {
        this.executor = executor;
    }

    @Override
    public <T> List<T> query(KKBConfiguration configuration, KKBMappedStatement mappedStatement, Map<String, Object> param) {
        // 查询二级缓存

        // 没有，查一级缓存
        return executor.query(configuration, mappedStatement,param);
    }
}
