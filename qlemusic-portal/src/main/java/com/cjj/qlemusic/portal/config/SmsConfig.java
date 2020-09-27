package com.cjj.qlemusic.portal.config;

import cn.hutool.core.date.DateUtil;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Date;

/**
 * 阿里云短信配置类
 */
@Configuration
public class SmsConfig {
    @Value("${aliyuncs.sendSms.regionId}")
    private String regionId;
    @Value("${aliyun.oss.accessKeyId}")
    private String accessKeyId;
    @Value("${aliyun.oss.accessKeySecret}")
    private String accessKeySecret;
    @Value("${aliyuncs.sendSms.endpoint}")
    private String endpoint;
    @Value("${aliyuncs.sendSms.version}")
    private String version;
    @Value("${aliyuncs.type.sendSms}")
    private String type;
    @Value("${aliyuncs.sendSms.parameters.regionIdKey}")
    private String regionIdKey;
    @Value("${aliyuncs.sendSms.parameters.signNameKey}")
    private String signNameKey;
    @Value("${aliyuncs.sendSms.parameters.templateCodeKey}")
    private String templateCodeKey;
    @Value("${aliyuncs.sendSms.signName}")
    private String signName;
    @Value("${aliyuncs.sendSms.templateCode}")
    private String templateCode;

    @Bean
    public IAcsClient client(){
        DefaultProfile profile = DefaultProfile.getProfile(regionId, accessKeyId, accessKeySecret);
        return new DefaultAcsClient(profile);
    }

    @Bean
    public CommonRequest request(){
        CommonRequest request = new CommonRequest();
        request.setSysMethod(MethodType.POST);
        request.setSysDomain(endpoint);
        request.setSysVersion(version);
        request.setSysAction(type);
        request.putQueryParameter(regionIdKey, regionId);
        request.putQueryParameter(signNameKey, signName);
        request.putQueryParameter(templateCodeKey, templateCode);
        return request;
    }
}
