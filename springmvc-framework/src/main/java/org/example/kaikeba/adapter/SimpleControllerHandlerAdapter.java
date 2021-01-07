package org.example.kaikeba.adapter;

import org.example.kaikeba.adapter.iface.HandlerAdapter;
import org.example.kaikeba.handler.iface.SimpleControllerHandler;
import org.example.kaikeba.model.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 专门用来适配SimpleControllerHandler类型的处理器类
 * 将该处理器类SimpleControllerHandler适配成统一的类型HandlerAdapter接口（类型）
 */

public class SimpleControllerHandlerAdapter implements HandlerAdapter {
    @Override
    public ModelAndView handlerRequest(Object handler, HttpServletRequest req, HttpServletResponse resp) throws Exception {
        return ((SimpleControllerHandler) handler).HandleRequest(req, resp);
    }

    @Override
    public boolean support(Object handler) {
        return handler instanceof SimpleControllerHandler;
    }
}
