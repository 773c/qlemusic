package com.cjj.qlemusic.portal.config;

import com.qq.connect.QQConnectException;
import com.qq.connect.api.OpenID;
import com.qq.connect.api.qzone.UserInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;


/**
 * 第三方oauth2.0授权
 */
@Configuration
public class Oauth2Config {
    @Value("${oauth.appId}")
    private String appId;
    @Value("${oauth.appKey}")
    private String appKey;
    @Value("${oauth.redirectUrl}")
    private String redirectUrl;

    public String getOpenID(String accessToken) throws QQConnectException {
        return new OpenID(accessToken).getUserOpenID();
    }

    public UserInfo getUserInfo(String accessToken,String openID){
        return new UserInfo(accessToken,openID);
    }

}
