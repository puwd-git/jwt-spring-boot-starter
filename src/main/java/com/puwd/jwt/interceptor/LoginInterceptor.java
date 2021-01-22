package com.puwd.jwt.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.puwd.jwt.config.JwtProperties;
import com.puwd.jwt.dto.JwtUserDto;
import com.puwd.jwt.exception.UserNotFoundException;
import com.puwd.jwt.service.IJwtUserService;
import com.puwd.jwt.util.JwtUtil;
import com.puwd.jwt.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.puwd.jwt.util.JwtUtil.TOKEN_KEY;

/**
 * @auther puwd
 * @Date 2021-1-20 16:41
 * @Description
 */
public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    private IJwtUserService iJwtUserService;

    @Autowired
    private JwtProperties jwtProperties;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 允许跨域
        response.setHeader("Access-Control-Allow-Origin", "*");

        String username = request.getParameter("username");

        String password = request.getParameter("password");

        JwtUserDto jwtUserDto = iJwtUserService.getJwtUserDtoByUserName(username, password);

        if(jwtUserDto == null){
            request.setAttribute("exception", new UserNotFoundException("未找到用户信息"));
            request.getRequestDispatcher("/exception").forward(request, response);
            return true;
        }
        String token = JwtUtil.createToken(jwtUserDto, jwtProperties.getExpire());

        ResultVo<String> resultVo = new ResultVo.ResultVoBuilder<String>()
                .code(200)
                .isSuccess(true)
                .message("登陆成功")
                .data(token)
                .build();
        ObjectMapper objectMapper = new ObjectMapper();
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/json; charset=utf-8");
        response.addHeader(TOKEN_KEY,token);
        Cookie cookie = new Cookie(TOKEN_KEY,token);
        response.addCookie(cookie);
        response.getWriter().write(objectMapper.writeValueAsString(resultVo));
        return false;
    }
}
