package sqlnode;

import sqlnode.iface.KKBSqlNode;
import sqlnode.model.KKBDynamicContext;

/**
 * 封装不带有${}的SQL信息
 */
public class KKBStaticTextSqlNode implements KKBSqlNode {
    // 封装未解析的sql文本信息
    private String sqlText;

    public KKBStaticTextSqlNode(String sqlText) {
        this.sqlText = sqlText;
    }

    @Override
    public void apply(KKBDynamicContext dynamicContext) {
        dynamicContext.appendSql(sqlText);
    }
}
