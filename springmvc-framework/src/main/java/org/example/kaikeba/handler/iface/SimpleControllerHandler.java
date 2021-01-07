package org.example.kaikeba.handler.iface;


import org.example.kaikeba.model.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 制定编写处理类的规则--可以对返回值动手脚
 */
public interface SimpleControllerHandler {
    ModelAndView HandleRequest(HttpServletRequest req, HttpServletResponse resp) throws Exception;
}
