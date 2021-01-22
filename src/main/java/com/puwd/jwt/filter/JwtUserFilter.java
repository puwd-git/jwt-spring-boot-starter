package com.puwd.jwt.filter;


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
import java.io.IOException;

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

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;

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
        filterChain.doFilter(requestWrapper, servletResponse);
    }
}
