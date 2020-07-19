package com.costan.webshop.web.framework.router;

import com.costan.webshop.web.framework.ModelAndView;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

class ControllerMapping {

    private Object instance;
    private Map<String, Method> routes;

    ControllerMapping(final Object controllerInstance) {
        this.instance = controllerInstance;
        routes = new HashMap<>();
    }

    void addMethodPath(String path, Method method) {
        routes.put(path.replace("/", ""), method);
    }

    ModelAndView executeMethod(String path, String[] methodParameters) throws InvocationTargetException, IllegalAccessException {
        Method method = routes.get(path);
        return (ModelAndView) method.invoke(instance, (Object[])methodParameters);
    }

}
