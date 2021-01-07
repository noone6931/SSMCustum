package sqlnode;

import sqlnode.iface.KKBSqlNode;
import sqlnode.model.KKBDynamicContext;
import utils.KKBGenericTokenParser;
import utils.KKBOgnlUtils;
import utils.KKBSimpleTypeRegistry;
import utils.KKBTokenHandler;

import java.util.Map;

/**
 * 封装带有${}的SQL信息
 */
public class KKBTextSqlNode implements KKBSqlNode {

    // 封装未解析的sql文本信息
    private String sqlText;

    public KKBTextSqlNode(String sqlText) {
        this.sqlText = sqlText;
    }


    @Override
    public void apply(KKBDynamicContext dynamicContext) {
        // 用来处理${}中的参数
        // select * from user where name like '${name}%'


        // 用来解析SQL文本中的#{}或者${}
        BindingTokenHandler bindingTokenHandler = new BindingTokenHandler(dynamicContext);
        KKBGenericTokenParser genericTokenParser = new KKBGenericTokenParser("${", "}", bindingTokenHandler);
        // JDBC可以直接执行的SQL语句
        String sql = genericTokenParser.parse(sqlText);
        dynamicContext.appendSql(sql);
    }

    public boolean isDynamic() {
        return sqlText.contains("${");
    }


    class BindingTokenHandler implements KKBTokenHandler {

        private KKBDynamicContext dynamicContext;

        public BindingTokenHandler(KKBDynamicContext dynamicContext) {
            this.dynamicContext = dynamicContext;
        }

        /**
         * 使用参数值来替换${}
         *
         * @param content 是${}中的内容
         * @return 用来替换${}的值
         */
        @Override
        public String handleToken(String content) {
            Map<String, Object> bindings = dynamicContext.getBindings();
            // 获取参数值
            Object parameter = bindings.get("_parameter");
            if (parameter == null) {
                return "";
            }
            if (KKBSimpleTypeRegistry.isSimpleType(parameter.getClass())) {
                return parameter.toString();
            }
            // POJO类型或者Map类型
            Object value = KKBOgnlUtils.getValue(content, parameter);
            if (value == null) {
                return "";
            } else {
                return value.toString();
            }
        }
    }


}
