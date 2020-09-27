package com.cjj.qlemusic.common.constant;

/**
 * 状态码
 */
public enum StatusCodeConstant {
    SUCCESS(200,"操作成功"),
    FAILED(500,"操作失败"),
    VERIFYCODE_FAILED(409,"验证码错误"),
    PARAMVALID_FAILED(404,"参数检验失败"),
    UNAUTHENTICATED(403,"未登录或token已过期"),
    UNAUTHORIZED(401,"未授权");

    private long statusCode;
    private String message;

    private StatusCodeConstant(long statusCode,String message){
        this.statusCode=statusCode;
        this.message=message;
    }

    public long getStatusCode() {
        return statusCode;
    }

    public String getMessage() {
        return message;
    }
}
