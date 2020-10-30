package com.cjj.qlemusic.security.service;

import com.qq.connect.QQConnectException;
import com.qq.connect.api.qzone.UserInfo;
import com.qq.connect.javabeans.AccessToken;

import javax.servlet.ServletRequest;

/**
 * 第三方授权Service
 */
public interface OauthService {
    /**
     * 获取Authorization Code
     * @param request
     * @return
     */
    String getAuthorizaUrl(ServletRequest request) throws QQConnectException;

    /**
     * 获取AccessToken
     * @param request
     * @return
     */
    AccessToken getAccessToken(ServletRequest request) throws QQConnectException;

    /**
     * 获取OpenID
     * @param accessToken
     * @return
     * @throws QQConnectException
     */
    String getOpenID(String accessToken) throws QQConnectException;

    /**
     * 获取用户信息
     * @param accessToken
     * @param openID
     * @return
     */
    UserInfo getUserInfo(String accessToken, String openID);
}
