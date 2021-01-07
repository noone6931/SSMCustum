package sqlnode.iface;

import sqlnode.model.KKBDynamicContext;

/**
 * 提供对于SQL节点信息的处理功能
 */
public interface KKBSqlNode {
    void apply(KKBDynamicContext dynamicContext);
}
