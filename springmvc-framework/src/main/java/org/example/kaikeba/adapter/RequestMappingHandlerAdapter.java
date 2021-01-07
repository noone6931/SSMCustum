package org.example.kaikeba.adapter;

import org.example.kaikeba.adapter.iface.HandlerAdapter;
import org.example.kaikeba.annotation.ResponseBody;
import org.example.kaikeba.model.HandlerMethod;
import org.example.kaikeba.model.ModelAndView;
import org.example.kaikeba.utils.JsonUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
/**
 * 专门用来适配注解方式的处理器类HandlerMethod
 * 将该处理器类HandlerMethod适配成统一的类型HandlerAdapter接口（类型）
 */
public class RequestMappingHandlerAdapter implements HandlerAdapter {
    @Override
    public ModelAndView handlerRequest(Object handler, HttpServletRequest req, HttpServletResponse resp) throws Exception {
        // 获取HandlerMethod中的Controller对象和Method对象
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Object controller = handlerMethod.getController();
        Method method = handlerMethod.getMethod();
        // 处理参数
        Object[] args = handleParameter(req, method);
        // 通过反射调用
        Object returnValue = method.invoke(controller, args);
        // 处理返回值
        handleResult(returnValue, method, resp);
        return null;
    }

    private void handleResult(Object returnValue, Method method, HttpServletResponse resp) throws IOException {
        if (method.isAnnotationPresent(ResponseBody.class)) {
            if (returnValue instanceof String) {
                resp.setContentType("text/plain;charset=utf8");
                resp.getWriter().write("保存用户");
            } else if (returnValue instanceof Map) {
                resp.setContentType("application/json;charset=utf8");
                resp.getWriter().write(Objects.requireNonNull(JsonUtils.object2Json(returnValue)));
            }
        } else {

        }


    }


    private Object[] handleParameter(HttpServletRequest req, Method method) {

        List<Object> paramList = new ArrayList<>();
        // 将Request请求中的KV数据，设置给Method对象的参数中
        Map<String, String[]> parameterMap = req.getParameterMap();
        // 获取@RequestMapping注释的方法的参数列表
        Parameter[] parameters = method.getParameters();
        for (Parameter parameter : parameters) {
            // 如果不进行特殊处理，在此处获取的名称，回收args0,arg1这样的参数
            String name = parameter.getName();
            // 方法参数的名称一定要和请求中的key保持一致
            String[] strings = parameterMap.get(name);

            // 将请求中的值按照需求进行类型转换
            Object valueToUse = resolveValue(strings, parameter.getType());
            paramList.add(valueToUse);
        }
        return paramList.toArray();
    }

    private Object resolveValue(String[] strings, Class<?> type) {
        if (type == String.class) {
            return strings[0];
        } else if (type == Integer.class) {
            return Integer.parseInt(strings[0]);
        }//....

        return null;
    }

    @Override
    public boolean support(Object handler) {
        return handler instanceof HandlerMethod;
    }
}
