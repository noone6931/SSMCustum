package org.example.kaikeba.servlet;

import org.example.kaikeba.adapter.iface.HandlerAdapter;
import org.example.kaikeba.mapping.iface.HandlerMapping;
import org.example.spring.factory.support.DefaultListableBeanFactory;
import org.example.spring.reader.XMLBeanDefinitionReader;
import org.example.spring.resource.ClasspathResource;
import org.example.spring.resource.Resource;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * 请求分发
 */
public class DispatcherServlet extends AbstractServlet {

    // 存储所有的HandlerMapping集合
    List<HandlerMapping> handlerMappings = new ArrayList<>();
    // 存储所有的HandlerAdapter集合
    List<HandlerAdapter> handlerAdapters = new ArrayList<>();
    private DefaultListableBeanFactory beanFactory;

    @Override
    public void init(ServletConfig config) throws ServletException {
        // 从web.xml中的servlet子标签中获取init-parameter子标签的value
        String location = config.getInitParameter("contextConfigLocation");
        initSpringContainer(location);
        initStrategies();
    }

    private void initStrategies() {
        handlerMappings = beanFactory.getBeansByType(HandlerMapping.class);
        handlerAdapters = beanFactory.getBeansByType(HandlerAdapter.class);
    }

    private void initSpringContainer(String location) {
        beanFactory = new DefaultListableBeanFactory();
        Resource resource = new ClasspathResource(location);
        InputStream inputStream = resource.getResource();
        XMLBeanDefinitionReader beanDefinitionReader = new XMLBeanDefinitionReader(beanFactory);
        beanDefinitionReader.registerBeanDefinitions(inputStream);
        // 加载Spring容器中所有的单例bean
        beanFactory.getBeansByType(Object.class);
    }

    @Override
    protected void doDispatch(HttpServletRequest req, HttpServletResponse resp) {
        try {
            // 请求分发给谁？ 其实就是分发给对应的请求处理类
            // 注意：该请求处理类，不需要继承或者实现任何和Servlet相关的类，它只是一个普通的java类

            // 如何找到处理类（定义规则，也就是说，【处理类要存放在那，根据什么来找？】）
            // 定义了存储对象（HandlerMapping）
            Object handler = getHandler(req);

            if (handler == null) {
                return;
            }
            // 找到处理器对象之后，如何调用该对象的方法？找到的是Object类型
            // 如果找不到真正的处理器类型，是无法正常调用的
            // 所以这里需要一个处理器适配器
            HandlerAdapter ha = getHandlerAdapter(handler);
            ha.handlerRequest(handler, req, resp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private HandlerAdapter getHandlerAdapter(Object handler) {
        // 策略模式 + 适配器模式
        for (HandlerAdapter handlerAdapter : handlerAdapters) {
            // 策略是否合适
            if (handlerAdapter.support(handler)) {
                return handlerAdapter;
            }
        }
        return null;
    }

    private Object getHandler(HttpServletRequest req) {
        // 策略模式
        for (HandlerMapping handlerMapping : handlerMappings) {
            Object handler = handlerMapping.getHandler(req);
            // 策略是否合适
            if (handler != null) {
                return handler;
            }
        }
        return null;
    }
}
