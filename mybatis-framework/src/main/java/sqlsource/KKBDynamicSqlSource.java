package sqlsource;

import sqlnode.iface.KKBSqlNode;
import sqlnode.model.KKBDynamicContext;
import sqlsource.iface.KKBSqlSource;
import sqlsource.model.KKBBoundSql;
import utils.KKBGenericTokenParser;
import utils.KKBParameterMappingTokenHandler;

/**
 * 封装带有${}或者动态sql标签的信息
 * 注意：${}每次执行sql语句的时候，都要解析。
 * 也就是说每次调用getBoundSql方法的时候，去解析SqlNode
 */
public class KKBDynamicSqlSource implements KKBSqlSource {

    private KKBSqlNode rootSqlNode;

    public KKBDynamicSqlSource(KKBSqlNode rootSqlNode) {
        this.rootSqlNode = rootSqlNode;
    }

    public KKBSqlNode getRootSqlNode() {
        return rootSqlNode;
    }

    public void setRootSqlNode(KKBSqlNode rootSqlNode) {
        this.rootSqlNode = rootSqlNode;
    }

    @Override
    public KKBBoundSql getBoundSql(Object param) {
        // 1.处理所有的SqlNode，合并成一条SQL语句（该语句${}已经处理，而#{}还未处理）
        KKBDynamicContext dynamicContext = new KKBDynamicContext(param);
        rootSqlNode.apply(dynamicContext);
        // 合并之后的SQL语句
        // select * from user where id = #{id}
        String sqlText = dynamicContext.getSb().toString();
        System.out.println("#{}未处理的SQL语句：" + sqlText);

        // 2.处理#{}，得到JDBC可以执行的【SQL语句】，以及解析出来的【参数信息集合】
        // 用来处理#{}中的参数
        // 2.1 、将#{}替换为?----字符串处理
        // 2.2 、将#{}里面的参数名称，比如说id，封装成ParameterMapping对象中，并添加到List集合
        KKBParameterMappingTokenHandler parameterMappingTokenHandler = new KKBParameterMappingTokenHandler();
        // 用来解析SQL文本中的#{}或者${}
        KKBGenericTokenParser genericTokenParser = new KKBGenericTokenParser("#{", "}", parameterMappingTokenHandler);
        // JDBC可以直接执行的SQL语句
        String sql = genericTokenParser.parse(sqlText);
        System.out.println("#{}处理之后的SQL语句：" + sql);
        // 3.将得到的SQL语句和参数信息集合，封装到StaticSqlSource里面存储
        return new KKBBoundSql(sql, parameterMappingTokenHandler.getParameterMappings());
    }
}
