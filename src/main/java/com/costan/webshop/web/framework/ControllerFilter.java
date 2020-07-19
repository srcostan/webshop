package com.costan.webshop.web.framework;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class ControllerFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        String path = request.getRequestURI().substring(request.getContextPath().length());
        if (path.startsWith("/templates/") ||
                path.contains("/resources/")) {
            chain.doFilter(req, res);
        } else {
            req.getRequestDispatcher(path).forward(req, res);
        }
    }

    @Override
    public void destroy() {
    }
}
