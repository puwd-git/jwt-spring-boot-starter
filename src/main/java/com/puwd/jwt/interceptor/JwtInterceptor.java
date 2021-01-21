package com.puwd.jwt.interceptor;

import com.auth0.jwt.interfaces.Claim;
import com.puwd.jwt.util.JwtUtil;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

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

        String token = request.getHeader("authorization");

        if(token == null || "".equals(token)){
            Cookie[] cookies = request.getCookies();
            for (Cookie cookie : cookies) {
                if(cookie.getName().equals("authorization")){
                    token = cookie.getValue();
                }
            }
        }

        if(token == null || "".equals(token)){
            throw new RuntimeException("无token，请重新登录");
        }

        String id = JwtUtil.getUserId(token);

        Map<String, Claim> map =  JwtUtil.getClaims(token);

        return true;
    }
}
