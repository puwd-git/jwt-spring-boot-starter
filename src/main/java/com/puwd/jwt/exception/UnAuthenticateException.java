package com.puwd.jwt.exception;

/**
 * @auther puwd
 * @Date 2021-1-21 15:45
 * @Description
 */
public class UnAuthenticateException extends RuntimeException {

    /**
     * 错误信息
     */
    private final String errorMsg;


    public String getErrorMsg() {
        return errorMsg;
    }

    /**
     * Constructs a new runtime exception with {@code null} as its
     * detail message.  The cause is not initialized, and may subsequently be
     * initialized by a call to {@link #initCause}.
     */
    public UnAuthenticateException(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
