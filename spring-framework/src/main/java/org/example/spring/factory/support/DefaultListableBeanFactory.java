package org.example.spring.factory.support;

import org.example.spring.factory.ListableBeanFactory;
import org.example.spring.ioc.BeanDefinition;
import org.example.spring.registry.BeanDefinitionRegistry;

import java.util.*;

/**
 * 该类就是真正的Spring工厂实现类
 */
public class DefaultListableBeanFactory extends AbstractAutowireCapableBeanFactory implements BeanDefinitionRegistry, ListableBeanFactory {

    // 存储从XML或者注解中解析出来的BeanDefinition的Map集合
    private Map<String, BeanDefinition> beanDefinitions = new HashMap<>();


    @Override
    public BeanDefinition getBeanDefinition(String beanName) {
        return this.beanDefinitions.get(beanName);
    }

    @Override
    public void registerBeanDefinition(String beanName, BeanDefinition bd) {
        this.beanDefinitions.put(beanName, bd);
    }

    @Override
    public List<BeanDefinition> getBeanDefinitions() {
        return new ArrayList<>(this.beanDefinitions.values());
    }

    @Override
    public List getBeansByType(Class clazz) {
        List<Object> singletonObjects = new ArrayList<>();
        Collection<BeanDefinition> bds = beanDefinitions.values();
        for (BeanDefinition bd : bds) {
            Class<?> clazzType = bd.getClazzType();
            if (clazz.isAssignableFrom(clazzType)) {
                String beanName = bd.getBeanName();
                Object bean = getBean(beanName);
                singletonObjects.add(bean);
            }
        }
        return singletonObjects;
    }
}
