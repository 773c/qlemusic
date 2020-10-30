package com.cjj.qlemusic.security.service;


import com.aliyuncs.exceptions.ClientException;
import com.cjj.qlemusic.security.entity.*;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * 用户管理Service
 */
public interface UmsUserService {
    /**
     * 用户手机号注册
     * @param umsUserRegister
     * @return
     */
    @Transactional
    int register(UmsUserRegister umsUserRegister);

    /**
     * 匹配验证码
     * @param umsUserLogin
     * @return
     */
    boolean matchVerify(UmsUserLogin umsUserLogin);

    /**
     * 发送短信
     * @param telephone
     * @return
     */
    String sendSms(String telephone) throws ClientException;

    /**
     * 设置用户的默认信息
     * @param umsUser
     * @return
     */
    UmsUser setUserInfo(UmsUser umsUser);

    /**
     * 手机验证码登录
     * @param umsUserLogin
     * @return
     */
    String login(UmsUserLogin umsUserLogin);

    /**
     * 根据手机号查询用户
     * @param identity
     * @return
     */
    UmsUser getUserByIdentity(String identity);

    /**
     * 修改用户信息
     * @param umsUserInfo
     * @return
     */
    int updateUserInfo(UmsUserInfo umsUserInfo);

    /**
     * 修改用户唯一ID
     * @param umsUserInfo
     * @return
     */
    @Transactional
    int updateUniqueId(UmsUserInfo umsUserInfo);

    /**
     * 查询用户是否修改过唯一ID
     * @param id
     * @return
     */
    Long getUserByIdFromUniqueId(Long id);

    /**
     * 修改用户头像
     * @param id
     * @param uniqueId
     * @param file
     * @return
     */
    String updateAvatar(Long id,String uniqueId,MultipartFile file) throws IOException;

    /**
     * 查询用户
     * @param id
     * @return
     */
    UmsUserInfo getUserById(Long id);

}
