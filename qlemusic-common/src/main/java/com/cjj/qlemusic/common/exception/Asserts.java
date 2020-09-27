package com.cjj.qlemusic.common.exception;

/**
 * 断言处理类
 */
public class Asserts {
    public static void fail(String message){
        throw new ApiException(message);
    }
}
