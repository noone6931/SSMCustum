package sqlnode.model;

import java.util.HashMap;
import java.util.Map;

/**
 * 用来封装SqlNode执行过程中需要的参数信息以及用来拼接SqlNode执行过程中的SQL文本
 */
public class KKBDynamicContext {

    // 用于拼接SQL语句的StringBuffer对象
    private StringBuffer sb = new StringBuffer();

    // 用于封装解析SqlNode过程中需要的信息
    private Map<String, Object> bindings = new HashMap<>();

    // 必须传入参
    public KKBDynamicContext(Object param) {
        bindings.put("_parameter", param);
    }

    public StringBuffer getSb() {
        return sb;
    }

    //SQL文本拼接
    public void appendSql(String sql)   {
        sb.append(sql);
        sb.append(" ");
    }

    public Map<String, Object> getBindings() {
        return bindings;
    }

    public void addBindings(String key, Object value) {
        bindings.put(key, value);
    }
}
