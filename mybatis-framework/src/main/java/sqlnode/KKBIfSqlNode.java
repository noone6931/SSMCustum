package sqlnode;

import sqlnode.iface.KKBSqlNode;
import sqlnode.model.KKBDynamicContext;
import utils.KKBOgnlUtils;

import java.util.Map;

public class KKBIfSqlNode implements KKBSqlNode {
    // if标签的test属性(获取OGNL表达式)
    private String test;
    // if标签的子标签集合
    private KKBSqlNode rootSqlNode;

    public KKBIfSqlNode(String test, KKBSqlNode sqlNode) {
        this.test = test;
        this.rootSqlNode = sqlNode;
    }

    @Override
    public void apply(KKBDynamicContext dynamicContext) {
        Map<String, Object> bindings = dynamicContext.getBindings();
        Object parameter = bindings.get("_parameter");
        boolean evaluateBoolean = KKBOgnlUtils.evaluateBoolean(test, parameter);
        if (evaluateBoolean) {
            rootSqlNode.apply(dynamicContext);
        }
    }
}
