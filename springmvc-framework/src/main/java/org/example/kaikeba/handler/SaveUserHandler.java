package org.example.kaikeba.handler;


import org.example.kaikeba.handler.iface.HttpRequestHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SaveUserHandler implements HttpRequestHandler {
    @Override
    public void HandleRequest(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        resp.setContentType("text/plain;charset=utf8");
        resp.getWriter().write("保存用户");
    }
}
