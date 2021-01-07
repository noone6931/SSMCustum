package org.example.spring.factory;

import java.util.List;

/**
 * 该接口是可以对spring容器进行列表话操作的功能
 */
public interface ListableBeanFactory extends BeanFactory {
    /**
     * 根据给定的类型，去获取该类型和子类型对应的所有的Bean的实例
     *
     * @param clazz
     * @return
     */
    List getBeansByType(Class clazz);
}
