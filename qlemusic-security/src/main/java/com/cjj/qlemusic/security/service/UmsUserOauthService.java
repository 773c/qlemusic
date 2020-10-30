package com.cjj.qlemusic.security.service;

import com.cjj.qlemusic.security.entity.UmsUserInfo;
import com.qq.connect.QQConnectException;
import com.qq.connect.javabeans.AccessToken;
import com.qq.connect.javabeans.qzone.UserInfoBean;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


/**
 * oauth2.0第三方授权Service
 */
public interface UmsUserOauthService {
    /**
     * 保存qq用户授权信息
     * @param accessToken
     */
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    String saveQQUserInfo(AccessToken accessToken) throws QQConnectException;

    /**
     * 设置qq用户信息
     * @param umsUserInfo
     * @param userInfo
     * @return
     */
    UmsUserInfo setQQUserInfo(UmsUserInfo umsUserInfo, UserInfoBean userInfo);
}
