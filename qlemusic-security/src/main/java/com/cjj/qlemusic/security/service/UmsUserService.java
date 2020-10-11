package com.cjj.qlemusic.security.service;


import com.aliyuncs.exceptions.ClientException;
import com.cjj.qlemusic.security.entity.UmsUser;
import com.cjj.qlemusic.security.entity.UmsUserRegister;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * 用户管理Service
 */
public interface UmsUserService {
    /**
     * 用户手机号注册
     * @param umsUserRegister
     */
    UmsUser register(UmsUserRegister umsUserRegister);

    /**
     * 匹配验证码
     * @param umsUser
     * @return
     */
    boolean matchVerify(UmsUser umsUser);

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
     * 用户登录
     * @param umsUser
     * @return
     */
    String login(UmsUser umsUser);

    /**
     * 根据手机号查询用户
     * @param telephone
     * @return
     */
    UmsUser getUserByTelephone(String telephone);

    /**
     * 修改用户信息
     * @param umsUser
     * @return
     */
    int updateInfo(UmsUser umsUser);

    /**
     * 修改用户唯一ID
     * @param id
     * @param uniqueId
     * @return
     */
    int updateUniqueId(Long id,String uniqueId);

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
     * 添加默认收藏夹
     * @param regUser
     */
    void addDefaultCollect(UmsUser regUser);

    /**
     * 查询用户
     * @param id
     * @return
     */
    UmsUser getUserById(Long id);
}
