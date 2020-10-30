package com.cjj.qlemusic.portal.dao;

import com.cjj.qlemusic.portal.entity.UmsUserSetPassword;
import com.cjj.qlemusic.portal.entity.UmsUserSetTelephone;
import com.cjj.qlemusic.security.entity.UmsUser;
import com.cjj.qlemusic.security.entity.UmsUserInfo;

/**
 * 用户设置Dao
 */
public interface UmsUserSetDao {
    /**
     * 根据id查询用户
     * @param userId
     * @return
     */
    UmsUser selectUserById(Long userId);

    /**
     * 修改密码
     * @param umsUserSetPassword
     */
    void updatePassword(UmsUserSetPassword umsUserSetPassword);

    /**
     * 修改手机号
     * @param umsUserSetTelephone
     */
    void updateTelephone(UmsUserSetTelephone umsUserSetTelephone);

    /**
     * 根据手机号查询是否已经存在
     * @param newTelephone
     * @return
     */
    Long selectUserByNewTelephone(String newTelephone);

    /**
     * 根据id获取用户信息
     * @param userId
     * @return
     */
    UmsUserInfo selectUserInfoById(Long userId);

    /**
     * 更新用户信息中的手机号
     * @param umsUserSetTelephone
     */
    void updateUserInfoToTelephone(UmsUserSetTelephone umsUserSetTelephone);

    /**
     * 根据用户id查询条数
     * @param userId
     * @return
     */
    int selectUserCountByUserId(Long userId);
}
