package com.cjj.qlemusic.security.entity;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class OssPolicy implements Serializable{
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "访问身份验证用到的标识")
    private String accessKeyId;

    @ApiModelProperty(value = "用于表单上传的策略")
    private String policy;

    @ApiModelProperty(value = "对策略签名后的字符串")
    private String signature;

    @ApiModelProperty(value = "存储路径前缀")
    private String dir;

    @ApiModelProperty(value = "OSS访问域名")
    private String host;

    @ApiModelProperty(value = "上传成功回调的设置")
    private String callback;


    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getAccessKeyId() {
        return accessKeyId;
    }

    public void setAccessKeyId(String accessKeyId) {
        this.accessKeyId = accessKeyId;
    }

    public String getPolicy() {
        return policy;
    }

    public void setPolicy(String policy) {
        this.policy = policy;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getDir() {
        return dir;
    }

    public void setDir(String dir) {
        this.dir = dir;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getCallback() {
        return callback;
    }

    public void setCallback(String callback) {
        this.callback = callback;
    }
}
