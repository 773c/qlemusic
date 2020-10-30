package com.cjj.qlemusic.security.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONUtil;
import com.cjj.qlemusic.common.util.VerifyUtil;
import com.cjj.qlemusic.security.dao.UmsUserDao;
import com.cjj.qlemusic.security.entity.UmsUser;
import com.cjj.qlemusic.security.entity.UmsUserInfo;
import com.cjj.qlemusic.security.service.OauthService;
import com.cjj.qlemusic.security.service.UmsUserOauthService;
import com.cjj.qlemusic.security.util.JwtTokenUtil;
import com.qq.connect.QQConnectException;
import com.qq.connect.javabeans.AccessToken;
import com.qq.connect.javabeans.qzone.UserInfoBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * oauth2.0第三方授权Service实现类
 */
@Service
public class UmsUserOauthServiceImpl implements UmsUserOauthService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UmsUserOauthServiceImpl.class);

    @Value("${user.uniqueId}")
    private String uniqueId;
    @Value("${user.qqLoginType}")
    private String qqLoginType;

    @Autowired
    private OauthService oauthService;
    @Autowired
    private UmsUserDao umsUserDao;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Override
    public String saveQQUserInfo(AccessToken accessToken) throws QQConnectException {
        //获取qqID
        String openID = oauthService.getOpenID(accessToken.getAccessToken());
        //查询数据库是否存在该qq用户授权信息
        Long id = umsUserDao.selectUserIdByIdentity(openID);
        UmsUser umsUser = new UmsUser();
        //判断qq是否授权过
        if(id != null){
            //存在，更新access_token
            umsUser.setIdentity(openID);
            umsUser.setCredential(accessToken.getAccessToken());
            umsUserDao.updateUser(umsUser);
        } else{
            //不存在
            //获取qq用户信息
            UserInfoBean userInfo = oauthService.getUserInfo(accessToken.getAccessToken(), openID).getUserInfo();
            //身份标识
            umsUser.setIdentity(openID);
            //凭证
            umsUser.setCredential(accessToken.getAccessToken());
            //过期时间
            umsUser.setExpiredTime(accessToken.getExpireIn());
            //登录类型
            umsUser.setLoginType(qqLoginType);
            //是否可用
            umsUser.setAvailable(true);
            //用户信息
            umsUser.setUmsUserInfo(setQQUserInfo(umsUser.getUmsUserInfo(),userInfo));
            //第三方绑定
            umsUser.getUmsUserInfo().setOauthId(openID);
            LOGGER.info("用户授权信息：",JSONUtil.toJsonPrettyStr(umsUser));
            //存入用户信息
            umsUserDao.insertUserInfo(umsUser.getUmsUserInfo());
            //将id存入
            umsUser.getUmsUserInfo().setId(umsUser.getUmsUserInfo().getId());
            //存入用户授权信息
            umsUserDao.insertUser(umsUser);
        }
        //创建一个claims
        Map<String,Object> claims = new HashMap<>();
        claims.put(JwtTokenUtil.CLAIM_ACCOUNT,openID);
        return jwtTokenUtil.generateToken(claims);
    }

    @Override
    public UmsUserInfo setQQUserInfo(UmsUserInfo umsUserInfo, UserInfoBean userInfo) {
        umsUserInfo = new UmsUserInfo();
        //唯一标识ID
        umsUserInfo.setUniqueId(uniqueId+"_"+
                DateUtil.dayOfMonth(DateUtil.date())%10 +
                VerifyUtil.getSixNumber() +
                DateUtil.format(new Date(),"ss"));
        //用户头像
        umsUserInfo.setAvatar(userInfo.getAvatar().getAvatarURL100());
        //用户名称
        umsUserInfo.setName(userInfo.getNickname());
        //用户性别
        umsUserInfo.setSex(userInfo.getGender());
        //注册日期
        umsUserInfo.setCreateTime(new Date());
        return umsUserInfo;
    }
}
