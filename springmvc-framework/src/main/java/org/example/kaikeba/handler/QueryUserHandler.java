package org.example.kaikeba.handler;

import org.example.kaikeba.handler.iface.SimpleControllerHandler;
import org.example.kaikeba.model.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class QueryUserHandler implements SimpleControllerHandler {
    @Override
    public ModelAndView HandleRequest(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        resp.setContentType("text/plain;charset=utf8");
        resp.getWriter().write("查询用户");
        return new ModelAndView();
    }
}
