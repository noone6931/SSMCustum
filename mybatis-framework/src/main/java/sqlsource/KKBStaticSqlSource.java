package sqlsource;

import sqlsource.iface.KKBSqlSource;
import sqlsource.model.KKBBoundSql;
import sqlsource.model.KKBParameterMapping;

import java.util.List;

/**
 * 只需要封装已经解析#{}出来的SqlSource信息
 */
public class KKBStaticSqlSource implements KKBSqlSource {

    private String sql;
    private List<KKBParameterMapping> parameterMappingList;

    public KKBStaticSqlSource(String sql, List<KKBParameterMapping> parameterMappingList) {
        this.sql = sql;
        this.parameterMappingList = parameterMappingList;
    }

    @Override
    public KKBBoundSql getBoundSql(Object param) {
        return new KKBBoundSql(sql, parameterMappingList);
    }
}
