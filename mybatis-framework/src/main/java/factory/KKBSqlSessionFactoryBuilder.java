package factory;

import builder.KKBXMLConfigBuilder;
import config.KKBConfiguration;
import org.dom4j.Document;
import utils.KKBDocumentUtils;

import java.io.InputStream;
import java.io.Reader;

/**
 * 1.根据传入的流对象，获取configuration对象
 * 2.创建sqlSessionFactory对象
 */
public class KKBSqlSessionFactoryBuilder {

    private KKBConfiguration configuration;

    public KKBSqlSessionFactoryBuilder() {
        this.configuration = new KKBConfiguration();
    }

    public KKBSqlSessionFactory build(InputStream inputStream) {
        Document document = KKBDocumentUtils.getDocument(inputStream);
        KKBXMLConfigBuilder kkbXmlConfigBuilder = new KKBXMLConfigBuilder(configuration);
        KKBConfiguration configuration = kkbXmlConfigBuilder.parseConfiguration(document.getRootElement());
        return build(configuration);
    }

    public KKBSqlSessionFactory build(Reader reader) {
        return null;
    }


    private KKBSqlSessionFactory build(KKBConfiguration configuration) {
        return new KKBDefaultSqlSessionFactory(configuration);
    }
}
