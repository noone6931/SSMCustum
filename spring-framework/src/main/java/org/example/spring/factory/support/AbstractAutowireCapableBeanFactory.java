package org.example.spring.factory.support;

import org.example.spring.aware.Aware;
import org.example.spring.aware.BeanFactoryAware;
import org.example.spring.factory.AutowireCapableBeanFactory;
import org.example.spring.init.InitializingBean;
import org.example.spring.ioc.BeanDefinition;
import org.example.spring.ioc.PropertyValue;
import org.example.spring.resolver.BeanDefinitionValueResolver;
import org.example.spring.utils.ReflectUtils;

import java.util.List;

/**
 * 该抽象类，指定了getBean的操作步骤
 */
public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory implements AutowireCapableBeanFactory {

    @Override
    public Object doCreateBean(BeanDefinition bd) {
        // 创建Bean分为三步：
        // Bean的实例化
        Object bean = createBeanInstance(bd);
        // Bean的依赖注入
        populateBean(bean, bd);
        // Bean的初始化
        initializeBean(bean, bd);

        return bean;
    }

    /***
     * bean的创建（new）
     * @param bd
     * @return
     */
    private Object createBeanInstance(BeanDefinition bd) {
        // 通过工厂方法方式去创建Bean实例

        // 通过实例工厂去创建Bean实例

        // 通过构造器去创建Bean实例
        return ReflectUtils.newInstance(bd.getClazzType());
    }


    /**
     * 依赖注入
     *
     * @param bean
     * @param bd
     */
    private void populateBean(Object bean, BeanDefinition bd) {
        List<PropertyValue> propertyValues = bd.getPropertyValues();
        for (PropertyValue pv : propertyValues) {
            // 属性名称
            String name = pv.getName();
            // 获取属性值（不能直接注入，需要处理）
            Object value = pv.getValue();

            // 将Object类型的value，转换成指定类型的value
            BeanDefinitionValueResolver valueResolver = new BeanDefinitionValueResolver(this);
            Object valueToUse = valueResolver.resoleValue(value);
            ReflectUtils.setPropertyValue(bean, name, valueToUse, bd.getClazzType());
        }
    }

    /**
     * 调用Bean的初始化功能
     *
     * @param bean
     * @param bd
     */
    private void initializeBean(Object bean, BeanDefinition bd) {
        //班班很漂亮
        //  初始化的时候，可以通过Aware标记接口，进行一些特殊信息的注入，比如BeanFactory的注入
        if (bean instanceof Aware) {
            // 远程注入BeanFactory给没有直接依赖关系的Bean
            ((BeanFactoryAware) bean).setBeanFactory(this);
        }
        // bean标签配置的Class，它是实现了InitializingBean接口，也是会初始化调用该接口的afterPropertiesSet方法
        if (bean instanceof InitializingBean) {
            ((InitializingBean) bean).afterPropertiesSet();
        }

        // bean标签如果配置了init-method属性，那么属性值就是初始化方法名称
        invokeInitMethod(bean, bd);
    }

    private void invokeInitMethod(Object bean, BeanDefinition bd) {
        try {
            String initMethod = bd.getInitMethod();
            if (initMethod == null || "".equals(initMethod)) {
                return;
            }
            ReflectUtils.invoke(bean, initMethod, bd.getClazzType());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
