package org.example.spring.factory.support;

import org.example.spring.factory.BeanFactory;
import org.example.spring.ioc.BeanDefinition;
import org.example.spring.registry.DefaultSingletonObjectRegistry;

/**
 * 该抽象类，指定了getBean的具体操作
 */
public abstract class AbstractBeanFactory extends DefaultSingletonObjectRegistry implements BeanFactory {
    public Object getBean(String beanName) {
        // beanName和bean对象的映射关系，可以考虑配置到xml文件中
        // bean对象的创建，只要知道该类的【全路径名称】，就可以通过反射去创建出来该对象
        // bean还需要依赖注入，其实可以通过反射去注入，但是需要知道要注入的对象，以及对象的属性名称，以及要注入的属性值
        // 先从单例Bean集合中去获取对应的Bean
        Object bean = getSingleton(beanName);
        // 如果有，直接返回
        if (bean != null) {
            return bean;
        }
        // 如果没有，则走创建流程
        // 需要根据beanName获取到对应的BeanDefinition对象
        BeanDefinition bd = getBeanDefinition(beanName);
        if (bd == null) {
            return null;
        }
        // 判断要创建的Bean是单例还是多例
        if (bd.isSingleton()) {
            bean = doCreateBean(bd);
            // 单例bean创建完成之后，要存储到单例Bean集合中
            addSingleton(beanName, bean);
        } else if (bd.isPrototype()) {
            // 多例（原型）bean创建完成之后，不需要存储到单例Bean集合中
            bean = doCreateBean(bd);
        }
        return bean;
    }

    /**
     * 获取BeanDefinition的抽象方法，需要子类去实现
     * 使用的是抽象模板方法设计模式
     *
     * @param beanName
     * @return
     */
    protected abstract BeanDefinition getBeanDefinition(String beanName);

    /**
     * 创建Bean对象的抽象方法
     *
     * @param bd
     * @return
     */
    protected abstract Object doCreateBean(BeanDefinition bd);
}