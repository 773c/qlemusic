package com.cjj.qlemusic.portal.service;

import com.cjj.qlemusic.portal.entity.UmsUserSetPassword;
import com.cjj.qlemusic.portal.entity.UmsUserSetTelephone; /**
 * 用户设置Service
 */
public interface UmsUserSetService {
    /**
     * 修改密码
     * @param umsUserSetPassword
     * @return
     */
    int updatePassword(UmsUserSetPassword umsUserSetPassword);

    /**
     * 修改手机号
     * @param umsUserSetTelephone
     * @return
     */
    int updateTelephone(UmsUserSetTelephone umsUserSetTelephone);
}
