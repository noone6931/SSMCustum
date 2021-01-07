package org.example.kaikeba.mapping;


import org.example.kaikeba.mapping.iface.HandlerMapping;
import org.example.spring.aware.BeanFactoryAware;
import org.example.spring.factory.BeanFactory;
import org.example.spring.factory.support.DefaultListableBeanFactory;
import org.example.spring.init.InitializingBean;
import org.example.spring.ioc.BeanDefinition;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用来存储处理类的映射关系，KEY就是请求URL，VALUE就是处理类的对象
 */
public class BeanNameUrlHandlerMapping implements HandlerMapping, BeanFactoryAware, InitializingBean {

    private Map<String, Object> urlHandlers = new HashMap<>();

    private DefaultListableBeanFactory beanFactory;

    @Override
    public Object getHandler(HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        return this.urlHandlers.get(requestURI);
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) {
        this.beanFactory = (DefaultListableBeanFactory) beanFactory;
    }

    @Override
    public void afterPropertiesSet() {
        List<BeanDefinition> beanDefinitions = beanFactory.getBeanDefinitions();
        for (BeanDefinition beanDefinition : beanDefinitions) {
            String beanName = beanDefinition.getBeanName();
            if (beanName.startsWith("/")) {
                this.urlHandlers.put(beanName, beanFactory.getBean(beanName));
            }
        }
    }
}
