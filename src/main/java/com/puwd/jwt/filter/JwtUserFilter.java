package com.puwd.jwt.filter;


import com.puwd.jwt.config.JwtProperties;
import com.puwd.jwt.dto.JwtUserDto;
import com.puwd.jwt.exception.UnAuthenticateException;
import com.puwd.jwt.exception.UserNotFoundException;
import com.puwd.jwt.service.IJwtUserService;
import com.puwd.jwt.util.JwtUtil;
import com.puwd.jwt.wrapper.JwtUserRequestWrapper;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

import static com.puwd.jwt.util.JwtUtil.TOKEN_KEY;

/**
 * @auther puwd
 * @Date 2021-1-21 16:14
 * @Description
 */
@WebFilter(urlPatterns = "/authc/*")
public class JwtUserFilter implements Filter {

    @Autowired
    private IJwtUserService iJwtUserService;

    @Autowired
    private JwtProperties jwtProperties;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;

        HttpServletResponse response = (HttpServletResponse) servletResponse;

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
            servletRequest.setAttribute("exception", new UnAuthenticateException("该用户未登录"));
            servletRequest.getRequestDispatcher("/exception").forward(servletRequest, servletResponse);
            filterChain.doFilter(servletRequest, servletResponse);
        }

        try {
            JwtUtil.getClaims(token);
        } catch (Exception e) {
            servletRequest.setAttribute("exception", e);
            servletRequest.getRequestDispatcher("/exception").forward(servletRequest, servletResponse);
            filterChain.doFilter(servletRequest, servletResponse);
        }

        String userId = JwtUtil.getUserId(token);

        JwtUserDto jwtUserDto = iJwtUserService.getJwtUserDtoByUserId(userId);

        if(jwtUserDto == null){
            servletRequest.setAttribute("exception", new UserNotFoundException("未找到用户信息"));
            servletRequest.getRequestDispatcher("/exception").forward(servletRequest, servletResponse);
            filterChain.doFilter(servletRequest, servletResponse);
        }

        JwtUserRequestWrapper requestWrapper = new JwtUserRequestWrapper(request);
        requestWrapper.addObject(jwtUserDto);

        // 获取token过期时间 小于十分钟续签
        Date expiresAt = JwtUtil.getExpiresAt(token);
        if((expiresAt.getTime()-System.currentTimeMillis()) < 1000 * 60 * 10){
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/json; charset=utf-8");
            token = JwtUtil.createToken(jwtUserDto, jwtProperties.getExpire());
            response.addHeader(TOKEN_KEY,token);
            Cookie cookie = new Cookie(TOKEN_KEY,token);
            response.addCookie(cookie);
        }
        filterChain.doFilter(requestWrapper, response);
    }
}
