package sqlnode;

import sqlnode.iface.KKBSqlNode;
import sqlnode.model.KKBDynamicContext;

import java.util.List;

public class KKBMixedSqlNode implements KKBSqlNode {

    private List<KKBSqlNode> sqlNodes;

    public KKBMixedSqlNode(List<KKBSqlNode> sqlNodes) {
        this.sqlNodes = sqlNodes;
    }

    @Override
    public void apply(KKBDynamicContext dynamicContext) {
        for (KKBSqlNode sqlNode : sqlNodes) {
            sqlNode.apply(dynamicContext);
        }
    }
}
