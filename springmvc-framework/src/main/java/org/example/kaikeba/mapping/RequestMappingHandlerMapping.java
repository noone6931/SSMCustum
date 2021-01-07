package org.example.kaikeba.mapping;

import org.example.kaikeba.annotation.Controller;
import org.example.kaikeba.annotation.RequestMapping;
import org.example.kaikeba.mapping.iface.HandlerMapping;
import org.example.kaikeba.model.HandlerMethod;
import org.example.spring.aware.BeanFactoryAware;
import org.example.spring.factory.BeanFactory;
import org.example.spring.factory.support.DefaultListableBeanFactory;
import org.example.spring.init.InitializingBean;
import org.example.spring.ioc.BeanDefinition;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用来存储处理类的映射关系，KEY就是请求URL，VALUE就是处理类的对象
 */
public class RequestMappingHandlerMapping implements HandlerMapping, BeanFactoryAware, InitializingBean {
    private Map<String, Object> urlHandlers = new HashMap<>();

    // 通过BeanFactoryAware接口注入了实例
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
            Class<?> clazzType = beanDefinition.getClazzType();
            String beanName = beanDefinition.getBeanName();
            // 判断读取的类是否是使用@Controller或者@RequestMapping注解修饰的类
            if (isHandlerClass(clazzType)) {
                Method[] declaredMethods = clazzType.getDeclaredMethods();
                for (Method declaredMethod : declaredMethods) {
                    RequestMapping clazzRequestMapping = clazzType.getAnnotation(RequestMapping.class);
                    StringBuffer sb = new StringBuffer();
                    String clazzUrl = clazzRequestMapping.value();
                    if (!clazzUrl.startsWith("/")) {
                        sb.append("/");
                    }
                    sb.append(clazzUrl);
                    if (isHandlerMethod(declaredMethod)) {
                        RequestMapping methodRequestMapping = declaredMethod.getAnnotation(RequestMapping.class);
                        String methodUrl = methodRequestMapping.value();
                        if (!methodUrl.startsWith("/")) {
                            sb.append("/");
                        }
                        sb.append(methodUrl);

                        HandlerMethod handlerMethod = new HandlerMethod( beanFactory.getBean(beanName), declaredMethod);
                        // KEY == 类上的@RequestMapping中的URL+方法上的URL
                        // VALUE == 获取@Controller注解的类的实例+@RequestMapping对应的方法Method
                        this.urlHandlers.put(sb.toString(), handlerMethod);
                    }
                }
            }
        }
    }

    private Boolean isHandlerClass(Class clazzType) {
        return clazzType.isAnnotationPresent(Controller.class) && clazzType.isAnnotationPresent(RequestMapping.class);
    }

    private Boolean isHandlerMethod(Method method) {
        return method.isAnnotationPresent(RequestMapping.class);
    }
}
