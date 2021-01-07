package org.example.kaikeba.adapter.iface;


import org.example.kaikeba.model.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 统一的适配类型
 */
public interface HandlerAdapter {
    /**
     * 处理请求
     *
     * @param handler
     * @param request
     * @param response
     * @return
     */
    ModelAndView handlerRequest(Object handler, HttpServletRequest req, HttpServletResponse resp) throws Exception;

    /**
     * 适配器是否支持该处理器
     *
     * @param handler
     * @return
     */
    boolean support(Object handler);
}
