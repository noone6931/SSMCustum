package sqlsource.iface;

import sqlsource.model.KKBBoundSql;


//提供对封装的SQL信息进行处理的操作
public interface KKBSqlSource {
    KKBBoundSql getBoundSql(Object param);
}
