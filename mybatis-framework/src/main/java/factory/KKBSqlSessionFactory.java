package factory;

import config.KKBConfiguration;
import sqlsession.KKBSqlSession;

public interface KKBSqlSessionFactory {

    KKBSqlSession openSession();
}
