package org.example.kaikeba.adapter;

import org.example.kaikeba.adapter.iface.HandlerAdapter;
import org.example.kaikeba.handler.iface.HttpRequestHandler;
import org.example.kaikeba.model.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 专门用来适配HttpRequestHandler类型的处理器类
 * 将该处理器类HttpRequestHandler适配成统一的类型HandlerAdapter接口（类型）
 */
public class HttpRequestHandlerAdapter implements HandlerAdapter {
    @Override
    public ModelAndView handlerRequest(Object handler, HttpServletRequest req, HttpServletResponse resp) throws Exception {
        ((HttpRequestHandler) handler).HandleRequest(req, resp);
        return null;
    }

    @Override
    public boolean support(Object handler) {
        return handler instanceof HttpRequestHandler;
    }
}
