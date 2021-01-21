package com.puwd.jwt.interceptor;

import com.puwd.jwt.util.JwtUtil;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @auther puwd
 * @Date 2021-1-20 16:41
 * @Description
 */
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 允许跨域
        response.setHeader("Access-Control-Allow-Origin", "*");

        String username = request.getHeader("username");

        String password = request.getHeader("password");
        String token = JwtUtil.createToken(null);
        response.addHeader("authorization",token);
        Cookie cookie = new Cookie("authorization",token);
        response.addCookie(cookie);
        response.getWriter().write(token);

        return false;
    }
}
