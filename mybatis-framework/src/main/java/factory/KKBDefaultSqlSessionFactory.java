package factory;

import config.KKBConfiguration;
import sqlsession.KKBDefaultSqlSession;
import sqlsession.KKBSqlSession;

public class KKBDefaultSqlSessionFactory implements KKBSqlSessionFactory {

    private KKBConfiguration configuration;

    public KKBDefaultSqlSessionFactory(KKBConfiguration configuration) {
        this.configuration = configuration;
    }

    @Override
    public KKBSqlSession openSession() {
        return new KKBDefaultSqlSession(configuration);
    }
}
