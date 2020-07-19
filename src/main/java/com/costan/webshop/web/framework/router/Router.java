package com.costan.webshop.web.framework.router;

import com.costan.webshop.web.framework.ModelAndView;
import com.costan.webshop.web.framework.annotation.Path;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class Router {

    private Map<String, ControllerMapping> controllerRouter;

    public Router() {
        controllerRouter = new HashMap<>();
    }

    public void addController(Class controllerClass) {
        Path controllerPath = (Path) controllerClass.getDeclaredAnnotation(Path.class);
        try {
            Object controller = controllerClass.getDeclaredConstructor().newInstance();
            ControllerMapping mapping = new ControllerMapping(controller);
            for (Method method : controllerClass.getMethods()) {
                Path methodPath = method.getDeclaredAnnotation(Path.class);
                if (methodPath != null) {
                    mapping.addMethodPath(methodPath.value(), method);
                }
            }
            controllerRouter.put(controllerPath.value().replace("/", ""), mapping);

        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    public ModelAndView executeRouteAction(String path, String[] parameters) {
        String[]  paths = path.split("/");
        String controllerPath = paths[0];
        String methodPath = paths.length > 1 ? paths[1] : "";
        try {
            return controllerRouter.get(controllerPath).executeMethod(methodPath, parameters);
        } catch (InvocationTargetException | IllegalAccessException e) {
            throw new IllegalStateException(e);
        }
    }

}