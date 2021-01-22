package com.puwd.jwt.handler;

import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.InvalidClaimException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.puwd.jwt.exception.UnAuthenticateException;
import com.puwd.jwt.exception.UserNotFoundException;
import com.puwd.jwt.vo.ResultVo;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * @auther puwd
 * @Date 2021-1-21 15:10
 * @Description
 */
@RestControllerAdvice
public class JwtExceptionHandler {

    @ExceptionHandler(UnsupportedEncodingException.class)
    public ResultVo<String> errorHandler(UnsupportedEncodingException e) {
        return new ResultVo.ResultVoBuilder<String>()
                .code(500)
                .isSuccess(false)
                .message("JWTToken认证签名/解密出现UnsupportedEncodingException异常")
                .build();
    }

    @ExceptionHandler(AlgorithmMismatchException.class)
    public ResultVo<String> errorHandler(AlgorithmMismatchException e) {
        return new ResultVo.ResultVoBuilder<String>()
                .code(500)
                .isSuccess(false)
                .message("Algorithm验证异常AlgorithmMismatchException")
                .build();
    }

    @ExceptionHandler(TokenExpiredException.class)
    public ResultVo<String> errorHandler(TokenExpiredException e) {
        return new ResultVo.ResultVoBuilder<String>()
                .code(500)
                .isSuccess(false)
                .message("token已过期")
                .build();
    }

    @ExceptionHandler(InvalidClaimException.class)
    public ResultVo<String> errorHandler(InvalidClaimException e) {
        return new ResultVo.ResultVoBuilder<String>()
                .code(500)
                .isSuccess(false)
                .message("Claims验证异常InvalidClaimException")
                .build();
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResultVo<String> errorHandler(NoHandlerFoundException e) {
        return new ResultVo.ResultVoBuilder<String>()
                .code(404)
                .isSuccess(false)
                .message("未找到处理器" + e.getHttpMethod() + " " + e.getRequestURL())
                .build();
    }

    @ExceptionHandler(UnAuthenticateException.class)
    public ResultVo<String> errorHandler(UnAuthenticateException e) {
        return new ResultVo.ResultVoBuilder<String>()
                .code(401)
                .isSuccess(false)
                .message(e.getErrorMsg())
                .build();
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResultVo<String> errorHandler(UserNotFoundException e) {
        return new ResultVo.ResultVoBuilder<String>()
                .code(500)
                .isSuccess(false)
                .message(e.getErrorMsg())
                .build();
    }

    @ExceptionHandler(Exception.class)
    public ResultVo<String> errorHandler(Exception e) {
        return new ResultVo.ResultVoBuilder<String>()
                .code(500)
                .isSuccess(false)
                .message(e.getMessage())
                .build();
    }
}
