package org.example.spring.registry;

import java.util.HashMap;
import java.util.Map;

/**
 * 封装了所有单例bean实例的集合
 */
public class DefaultSingletonObjectRegistry implements SingletonObjectRegistry {
    // 存储单例Bean的Map集合
    private Map<String, Object> singletonObjects = new HashMap<>();

    @Override
    public Object getSingleton(String beanName) {
        return this.singletonObjects.get(beanName);
    }

    @Override
    public void addSingleton(String beanName, Object bean) {
        //TODO 单例模式（双重检查锁方式）
        this.singletonObjects.put(beanName, bean);
    }
}
