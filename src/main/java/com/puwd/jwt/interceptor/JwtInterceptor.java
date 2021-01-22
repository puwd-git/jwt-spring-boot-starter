package com.puwd.jwt.interceptor;

import com.puwd.jwt.exception.UnAuthenticateException;
import com.puwd.jwt.util.JwtUtil;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.puwd.jwt.util.JwtUtil.TOKEN_KEY;

/**
 * @auther puwd
 * @Date 2021-1-20 16:23
 * @Description
 */
public class JwtInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 允许跨域
        response.setHeader("Access-Control-Allow-Origin", "*");

        String token = request.getHeader(TOKEN_KEY);

        if(token == null || "".equals(token)){
            Cookie[] cookies = request.getCookies();
            for (Cookie cookie : cookies) {
                if(cookie.getName().equals(TOKEN_KEY)){
                    token = cookie.getValue();
                }
            }
        }

        if(token == null || "".equals(token)){
            request.setAttribute("exception", new UnAuthenticateException("该用户未登录"));
            request.getRequestDispatcher("/exception").forward(request, response);
            return false;
        }

        try {
            JwtUtil.getClaims(token);
        } catch (Exception e) {
            request.setAttribute("exception", e);
            request.getRequestDispatcher("/exception").forward(request, response);
            return false;
        }

        return true;
    }
}
