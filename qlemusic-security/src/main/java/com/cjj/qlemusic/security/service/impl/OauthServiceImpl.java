package com.cjj.qlemusic.security.service.impl;

import com.cjj.qlemusic.security.service.OauthService;
import com.qq.connect.QQConnectException;
import com.qq.connect.api.OpenID;
import com.qq.connect.api.qzone.UserInfo;
import com.qq.connect.javabeans.AccessToken;
import com.qq.connect.oauth.Oauth;
import org.springframework.stereotype.Service;

import javax.servlet.ServletRequest;

/**
 * 第三方授权Service实现类
 */
@Service
public class OauthServiceImpl implements OauthService {

    public String getAuthorizaUrl(ServletRequest request) throws QQConnectException {
        return new Oauth().getAuthorizeURL(request);
    }

    @Override
    public AccessToken getAccessToken(ServletRequest request) throws QQConnectException {
        return new Oauth().getAccessTokenByRequest(request);
    }

    @Override
    public String getOpenID(String accessToken) throws QQConnectException {
        return new OpenID(accessToken).getUserOpenID();
    }

    @Override
    public UserInfo getUserInfo(String accessToken, String openID) {
        return new UserInfo(accessToken,openID);
    }
}
