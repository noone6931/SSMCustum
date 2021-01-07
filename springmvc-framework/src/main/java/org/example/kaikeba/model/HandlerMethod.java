package org.example.kaikeba.model;

import java.lang.reflect.Method;
//封装了@Controller注解的类的实例和@RequestMapping方法的Method对象
public class HandlerMethod {

    private Object Controller;
    private Method method;

    public HandlerMethod(Object controller, Method method) {
        Controller = controller;
        this.method = method;
    }

    public Object getController() {
        return Controller;
    }

    public void setController(Object controller) {
        Controller = controller;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }
}
