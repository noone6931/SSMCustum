package sqlsource;

import sqlnode.iface.KKBSqlNode;
import sqlnode.model.KKBDynamicContext;
import sqlsource.iface.KKBSqlSource;
import sqlsource.model.KKBBoundSql;
import sqlsource.model.KKBParameterMapping;
import utils.KKBGenericTokenParser;
import utils.KKBParameterMappingTokenHandler;

import java.util.List;


/**
 * 封装的是不带有${}或者动态SQL标签的信息，比如#{}
 * 注意：#{}只需要解析一次就可以了。
 * 也就是说不能每次调用getBoundSql方法的时候，去解析SqlNode
 * 只能在构造方法中去解析SqlNode
 */
public class KKBRawSqlSource implements KKBSqlSource {

    // 这里是staticSqlSource
    private KKBSqlSource sqlSource;

    public KKBRawSqlSource(KKBSqlNode rootSqlNode) {
        // 解析#{}
        // 1.处理所有的SqlNode，合并成一条SQL语句（该语句#{}还未处理）
        KKBDynamicContext dynamicContext = new KKBDynamicContext(null);
        rootSqlNode.apply(dynamicContext);
        // 合并之后的SQL语句
        // select * from user where id = #{id}
        String sqlText = dynamicContext.getSb().toString();
        // 2.处理#{}，得到JDBC可以执行的【SQL语句】，以及解析出来的【参数信息集合】
        // 用来处理#{}中的参数
        // 2.1 、将#{}替换为?----字符串处理
        // 2.2 、将#{}里面的参数名称，比如说id，封装成ParameterMapping对象中，并添加到List集合
        KKBParameterMappingTokenHandler parameterMappingTokenHandler = new KKBParameterMappingTokenHandler();
        // 用来解析SQL文本中的#{}或者${}
        KKBGenericTokenParser genericTokenParser = new KKBGenericTokenParser("#{", "}", parameterMappingTokenHandler);
        // JDBC可以直接执行的SQL语句
        String sql = genericTokenParser.parse(sqlText);
        List<KKBParameterMapping> parameterMappings = parameterMappingTokenHandler.getParameterMappings();
        // 3.将得到的SQL语句和参数信息集合，封装到StaticSqlSource里面存储
        sqlSource = new KKBStaticSqlSource(sql, parameterMappings);
    }

    @Override
    public KKBBoundSql getBoundSql(Object param) {
        return sqlSource.getBoundSql(param);
    }
}
