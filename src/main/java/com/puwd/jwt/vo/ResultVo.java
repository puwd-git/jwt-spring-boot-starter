package com.puwd.jwt.vo;


import java.io.Serializable;

/**
 * @auther puwd
 * @Date 2020-11-5 16:06
 * @Description
 */
public class ResultVo<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer code;

    private boolean success;

    private String message;

    private T data;

    private final long timestamp = System.currentTimeMillis();

    public ResultVo() {
    }

    public ResultVo(Integer code, boolean success, String message, T data) {
        this.code = code;
        this.success = success;
        this.message = message;
        this.data = data;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public boolean isSuccess() {
        return this.success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return this.data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public static class ResultVoBuilder <T> {

        private Integer code;

        private boolean success;

        private String message;

        private T data;

        public ResultVoBuilder <T> code(Integer code){
            this.code = code;
            return this;
        }

        public ResultVoBuilder <T> isSuccess(boolean success){
            this.success = success;
            return this;
        }

        public ResultVoBuilder <T> message(String message){
            this.message = message;
            return this;
        }

        public ResultVoBuilder <T> data(T data){
            this.data = data;
            return this;
        }

        public ResultVo<T> build(){
            return new ResultVo<T>(code, success, message, data);
        }
    }
}
