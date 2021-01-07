package org.example.spring.registry;

import org.example.spring.ioc.BeanDefinition;

import java.util.List;

/**
 * 提供对于封装的BeanDefinition对象结合的操作功能
 */
public interface BeanDefinitionRegistry {

    /**
     * 根据指定名称获取BeanDefinition对象
     *
     * @param beanName
     * @return
     */
    BeanDefinition getBeanDefinition(String beanName);

    /**
     * 将新的BeanDefinition注册到集合中
     *
     * @param beanName
     * @param bd
     */
    void registerBeanDefinition(String beanName, BeanDefinition bd);

    /**
     * 获取容器中所有的BeanDefinition信息
     *
     * @return
     */
    List<BeanDefinition> getBeanDefinitions();

}
