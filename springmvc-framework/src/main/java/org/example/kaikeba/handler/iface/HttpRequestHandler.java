package org.example.kaikeba.handler.iface;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 制定编写处理类的规则
 */
public interface HttpRequestHandler {
    void HandleRequest(HttpServletRequest req, HttpServletResponse resp) throws Exception;
}
