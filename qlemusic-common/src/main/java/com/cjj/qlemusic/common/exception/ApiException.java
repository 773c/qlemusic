package com.cjj.qlemusic.common.exception;

/**
 * 自定义api异常处理类
 */
public class ApiException extends RuntimeException{


    public ApiException(String message) {
        super(message);
    }

    public ApiException(String message, Throwable cause) {
        super(message, cause);
    }

    public ApiException(Throwable cause) {
        super(cause);
    }
}
