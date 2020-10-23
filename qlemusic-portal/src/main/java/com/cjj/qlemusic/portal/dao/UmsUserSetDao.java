package com.cjj.qlemusic.portal.dao;

import com.cjj.qlemusic.portal.entity.UmsUserSetPassword;
import com.cjj.qlemusic.portal.entity.UmsUserSetTelephone;
import com.cjj.qlemusic.security.entity.UmsUser;

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
     * 根据id获取手机号
     * @param userId
     * @return
     */
    String selectTelephoneById(Long userId);

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
}
