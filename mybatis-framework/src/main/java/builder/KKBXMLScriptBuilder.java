package builder;

import config.KKBConfiguration;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.Text;
import sqlnode.KKBIfSqlNode;
import sqlnode.KKBMixedSqlNode;
import sqlnode.KKBStaticTextSqlNode;
import sqlnode.KKBTextSqlNode;
import sqlnode.iface.KKBSqlNode;
import sqlsource.KKBDynamicSqlSource;
import sqlsource.KKBRawSqlSource;
import sqlsource.iface.KKBSqlSource;

import java.util.ArrayList;
import java.util.List;

public class KKBXMLScriptBuilder {

    private boolean isDynamic;


    public KKBSqlSource createSqlSource(Element selectElement) {
        return parseScriptNode(selectElement);
    }

    public KKBSqlSource parseScriptNode(Element selectElement) {
        KKBSqlNode mixedSqlNode = parseDynamicTags(selectElement);
        //如果带有${}或者动态SQL标签
        if (isDynamic) {
            return new KKBDynamicSqlSource(mixedSqlNode);
        } else {
            return new KKBRawSqlSource(mixedSqlNode);
        }
    }

    private KKBSqlNode parseDynamicTags(Element selectElement) {
        List<KKBSqlNode> sqlNodes = new ArrayList<>();
        //获取select标签的子元素 ：文本类型或者Element类型
        int nodeCount = selectElement.nodeCount();
        for (int i = 0; i < nodeCount; i++) {
            Node node = selectElement.node(i);
            if (node instanceof Text) {
                String sqlText = node.getText();
                if (sqlText == null || sqlText.trim().equals("")) {
                    continue;
                }
                // 先将sql文本封装到TextSqlNode中
                KKBTextSqlNode textSqlNode = new KKBTextSqlNode(sqlText.trim());
                if (textSqlNode.isDynamic()) {
                    isDynamic = true;
                    sqlNodes.add(textSqlNode);
                } else {
                    KKBStaticTextSqlNode staticTextSqlNode = new KKBStaticTextSqlNode(sqlText.trim());
                    sqlNodes.add(staticTextSqlNode);
                }
            } else if (node instanceof Element) {
                isDynamic = true;
                Element element = (Element) node;
                String name = element.getName();
                if ("if".equals(name)) {
                    String test = element.attributeValue("test");
                    //递归去解析子元素
                    KKBSqlNode mixedSqlNode = parseDynamicTags(element);
                    KKBIfSqlNode ifSqlNode = new KKBIfSqlNode(test, mixedSqlNode);
                    sqlNodes.add(ifSqlNode);
                }
            }
        }
        return new KKBMixedSqlNode(sqlNodes);
    }
}
