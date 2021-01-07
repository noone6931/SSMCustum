package org.example.spring.reader;

import org.dom4j.Document;
import org.example.spring.registry.BeanDefinitionRegistry;
import org.example.spring.utils.DocumentUtils;

import java.io.InputStream;

public class XMLBeanDefinitionReader {
    private BeanDefinitionRegistry beanDefinitionRegistry;

    public XMLBeanDefinitionReader(BeanDefinitionRegistry beanDefinitionRegistry) {
        this.beanDefinitionRegistry = beanDefinitionRegistry;
    }

    public void registerBeanDefinitions(InputStream inputStream){
        Document document = DocumentUtils.createDocument(inputStream);
        XMLBeanDefinitionDocumentReader xmlBeanDefinitionDocumentReader = new XMLBeanDefinitionDocumentReader(beanDefinitionRegistry);
        xmlBeanDefinitionDocumentReader.loadBeanDefinitions(document.getRootElement());
    }
}
