package sqlsession;

import java.util.List;
import java.util.Map;

/**
 * 顶级接口，给使用者提供便捷的调用
 */
public interface KKBSqlSession {
    <T> List<T> selectList(String s, Map<String, Object> param);

    <T> T selectOne(String s, Map<String, Object> param);
}
