package builder;

import config.KKBConfiguration;
import org.dom4j.Element;

import java.util.List;

public class KKBXMLMapperBuilder {
    private KKBConfiguration configuration;

    public KKBXMLMapperBuilder(KKBConfiguration configuration) {
        this.configuration = configuration;
    }

    public void parseMapper(Element rootElement) {
        String namespace = rootElement.attributeValue("namespace");
        // TODO 获取动态sql标签，比如<sql>
        // TODO 获取其他标签
        List<Element> elements = rootElement.elements("select");
        for (Element element : elements) {
            KKBXMLStatementBuilder kkbxmlStatementBuilder = new KKBXMLStatementBuilder(configuration);
            kkbxmlStatementBuilder.parseStatementElement(element, namespace);
        }
    }
}
