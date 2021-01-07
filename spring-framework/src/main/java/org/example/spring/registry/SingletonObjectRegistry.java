package org.example.spring.registry;

/**
 * 提供对于封装的Bean对象结合的操作功能
 */
public interface SingletonObjectRegistry {
    Object getSingleton(String beanName);

    void addSingleton(String beanName, Object bean);
}
