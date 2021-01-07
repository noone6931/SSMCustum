package builder;

import config.KKBConfiguration;
import config.KKBMappedStatement;
import org.dom4j.Element;
import sqlsource.iface.KKBSqlSource;
import utils.KKBReflectUtils;

public class KKBXMLStatementBuilder {

    private KKBConfiguration configuration;

    public KKBXMLStatementBuilder(KKBConfiguration configuration) {
        this.configuration = configuration;
    }

    /**
     * 解析映射文件中的select标签
     *
     * @param selectElement <select></select>
     */
    public void parseStatementElement(Element selectElement, String namespace) {
        String statementId = selectElement.attributeValue("id");
        if ("".equals(statementId) || statementId == null) {
            return;
        }
        // 一个CURD标签对应一个MappedStatement对象
        // 一个MappedStatement对象由一个statementId来标识，所以保证唯一性
        // statementId = namespace + "." + CRUD标签的id属性
        statementId = namespace + "." + statementId;
        String resultType = selectElement.attributeValue("resultType");
        Class<?> clazz = KKBReflectUtils.resolveType(resultType);
        // 注意：parameterType参数可以不设置也可以不解析
      /*  String parameterType = selectElement.attributeValue("parameterType");
        Class<?> parameterClass = resolveType(parameterType);*/
        String statementType = selectElement.attributeValue("statementType");
        statementType = statementType == null || "".equals(statementType) ? "prepared" : statementType;

        KKBXMLScriptBuilder kkbxmlScriptBuilder = new KKBXMLScriptBuilder();
        KKBSqlSource sqlSource = kkbxmlScriptBuilder.createSqlSource(selectElement);
        KKBMappedStatement mappedStatement = new KKBMappedStatement(statementId, sqlSource, statementType, clazz);
        configuration.addMappedStatementMap(statementId, mappedStatement);
    }


}
