package builder;

import config.KKBConfiguration;
import io.KKBResources;
import org.apache.commons.dbcp.BasicDataSource;
import org.dom4j.Document;
import org.dom4j.Element;
import utils.KKBDocumentUtils;

import java.io.InputStream;
import java.util.List;
import java.util.Properties;

public class KKBXMLConfigBuilder {

    private KKBConfiguration configuration;

    public KKBXMLConfigBuilder(KKBConfiguration configuration) {
        this.configuration = configuration;
    }

    public KKBConfiguration parseConfiguration(Element rootElement) {
        Element environments = rootElement.element("environments");
        parseEnvironments(environments);
        Element mappers = rootElement.element("mappers");
        parseMappers(mappers);
        return configuration;
    }

    private void parseEnvironments(Element environments) {
        String defaultEnvironment = environments.attributeValue("default");
        List<Element> environmentList = environments.elements("environment");
        for (Element environment : environmentList) {
            String id = environment.attributeValue("id");
            if (defaultEnvironment.equals(id)) {
                Element dataSource = environment.element("dataSource");
                parseDataSource(dataSource);
            }
        }
    }

    private void parseDataSource(Element dataSource) {
        String type = dataSource.attributeValue("type");
        BasicDataSource basicDataSource = new BasicDataSource();
        if ("DBCP".equals(type)) {
            Properties properties = parseProperties(dataSource);
            basicDataSource.setPassword(properties.getProperty("password"));
            basicDataSource.setDriverClassName(properties.getProperty("driver"));
            basicDataSource.setUsername(properties.getProperty("username"));
            basicDataSource.setUrl(properties.getProperty("url"));
        } else {
            // TODO
        }
        configuration.setDataSource(basicDataSource);

    }

    private Properties parseProperties(Element dataSource) {
        Properties properties = new Properties();
        List<Element> propertyList = dataSource.elements("property");
        for (Element element : propertyList) {
            properties.put(element.attributeValue("name"), element.attributeValue("value"));
        }
        return properties;
    }

    /**
     * 解析全局配置文件中的mappers标签
     *
     * @param mappers <mappers></mappers>
     */
    private void parseMappers(Element mappers) {
        List<Element> mapper = mappers.elements("mapper");
        for (Element element : mapper) {
            String resource = element.attributeValue("resource");
            InputStream resourceAsStream = KKBResources.getResourceAsStream(resource);
            Document document = KKBDocumentUtils.getDocument(resourceAsStream);

            KKBXMLMapperBuilder kkbxmlMapperBuilder = new KKBXMLMapperBuilder(configuration);
            kkbxmlMapperBuilder.parseMapper(document.getRootElement());
        }
    }


}
