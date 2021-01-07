package config;


import sqlsource.iface.KKBSqlSource;

public class KKBMappedStatement {

    private String statementId;
    private String resultType;
    private KKBSqlSource sqlSource;
    private String statementType;
    private Class resultClass;


    public KKBMappedStatement(String statementId, KKBSqlSource sqlSource, String statementType, Class resultClass) {
        this.statementId = statementId;
        this.sqlSource = sqlSource;
        this.statementType = statementType;
        this.resultClass = resultClass;
    }

    public KKBSqlSource getSqlSource() {
        return sqlSource;
    }

    public void setSqlSource(KKBSqlSource sqlSource) {
        this.sqlSource = sqlSource;
    }

    public Class getResultClass() {
        return resultClass;
    }

    public void setResultClass(Class resultClass) {
        this.resultClass = resultClass;
    }

    public String getStatementId() {
        return statementId;
    }

    public void setStatementId(String statementId) {
        this.statementId = statementId;
    }

    public String getResultType() {
        return resultType;
    }

    public void setResultType(String resultType) {
        this.resultType = resultType;
    }

    public String getStatementType() {
        return statementType;
    }

    public void setStatementType(String statementType) {
        this.statementType = statementType;
    }
}
