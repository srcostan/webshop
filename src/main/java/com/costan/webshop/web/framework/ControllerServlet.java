package com.costan.webshop.web.framework;

import com.costan.webshop.web.framework.router.Router;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ControllerServlet extends HttpServlet {

    private Router router;

    @Override
    public void init() throws ServletException {
        super.init();
        router = new Router();
        initControllers();
    }

    private void initControllers() throws ServletException {
        String[] controllersClassNames = new String[]{
                "com.costan.webshop.web.controller.FrontPageController",
                "com.costan.webshop.web.controller.ProductsController",
                "com.costan.webshop.web.controller.ShoppingCartController"
        };
        try {
            for (String controllerName : controllersClassNames) {
                Class controllerClass = Class.forName(controllerName);
                router.registerController(controllerClass);
            }
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getPathInfo().substring(1);
        String[] methodParameters = extractParameters(req.getQueryString());
        final ModelAndView modelAndView = router.executeRouteAction(path, methodParameters);
        modelAndView.getInternalModel().forEach((k, v) -> req.setAttribute(k, v));
        req.getRequestDispatcher(modelAndView.getView()).forward(req, resp);
    }

    private String[] extractParameters(String queryParams) {
        if (queryParams != null) {
            String[] queryParamsPairs = queryParams.split("&");
            String[] methodParameters = new String[queryParamsPairs.length];
            for (int i = 0; i < queryParamsPairs.length; i++) {
                String paramValue = queryParamsPairs[i].split("=")[1];
                methodParameters[i] = paramValue;
            }
            return methodParameters;
        }
        return new String[]{};
    }

}
