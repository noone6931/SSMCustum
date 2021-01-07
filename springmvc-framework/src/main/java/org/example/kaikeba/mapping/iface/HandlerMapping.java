package org.example.kaikeba.mapping.iface;

import javax.servlet.http.HttpServletRequest;

/**
 * 针对HandlerMapping实现类存储的处理类的映射数据，提供统一的接口访问
 */
public interface HandlerMapping {
    /**
     * 根据请求查找处理器对象
     * @param request
     * @return 返回的处理器对象，由于处理器的开发是比较灵活的，除了Object没有直接指定统一的开发规范
     */
    Object getHandler(HttpServletRequest request);
}
