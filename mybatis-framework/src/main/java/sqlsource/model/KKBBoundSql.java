package sqlsource.model;

import java.util.List;


/**
 * 用于封装解析之后的sql语句以及#{}解析出来的参数集合信息
 */
public class KKBBoundSql {
    // 封装解析之后的sql语句
    private String sql;
    // #{}解析出来的参数集合信息
    private List<KKBParameterMapping> parameterMappingList;

    public KKBBoundSql(String sql, List<KKBParameterMapping> parameterMappingList) {
        this.sql = sql;
        this.parameterMappingList = parameterMappingList;
    }


    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public List<KKBParameterMapping> getParameterMappings() {
        return parameterMappingList;
    }

    public void setParameterMappings(List<KKBParameterMapping> parameterMappingList) {
        this.parameterMappingList = parameterMappingList;
    }
}
