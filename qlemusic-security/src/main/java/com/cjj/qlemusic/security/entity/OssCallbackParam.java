package com.cjj.qlemusic.security.entity;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;


public class OssCallbackParam implements Serializable {
    private static final long SerialVersionUID = 1L;

    @ApiModelProperty(value = "回调成功的地址")
    private String callbackUrl;

    @ApiModelProperty(value = "回调时传入request的参数")
    private String callbackBody;

    @ApiModelProperty(value = "回调时传入参数的类型，比如表单提交类型")
    private String callbackBodyType;

    public static long getSerialVersionUID() {
        return SerialVersionUID;
    }

    public String getCallbackUrl() {
        return callbackUrl;
    }

    public void setCallbackUrl(String callbackUrl) {
        this.callbackUrl = callbackUrl;
    }

    public String getCallbackBody() {
        return callbackBody;
    }

    public void setCallbackBody(String callbackBody) {
        this.callbackBody = callbackBody;
    }

    public String getCallbackBodyType() {
        return callbackBodyType;
    }

    public void setCallbackBodyType(String callbackBodyType) {
        this.callbackBodyType = callbackBodyType;
    }
}
