package com.wyl.blog.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * @author russe
 */
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        final String user = "user";
        if (request.getSession().getAttribute(user) == null) {
            response.sendRedirect("/admin");
            return false;
        }
        return true;
    }
}
