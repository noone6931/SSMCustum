package org.example.kaikeba.mapping;

import org.example.kaikeba.handler.SaveUserHandler;
import org.example.kaikeba.mapping.iface.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 用来存储处理类的映射关系，KEY就是请求URL，VALUE就是处理类的对象
 */
public class SimpleUrlHandlerMapping implements HandlerMapping {
    private Map<String, Object> urlHandlers = new HashMap<>();

    public SimpleUrlHandlerMapping() {
        this.urlHandlers.put("/saveUser", new SaveUserHandler());
    }

    @Override
    public Object getHandler(HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        return this.urlHandlers.get(requestURI);
    }
}
