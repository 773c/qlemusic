package com.cjj.qlemusic.portal.service;

import com.cjj.qlemusic.portal.entity.UmsUserSetBindTelephone;
import com.cjj.qlemusic.portal.entity.UmsUserSetPassword;
import com.cjj.qlemusic.portal.entity.UmsUserSetTelephone;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
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
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    int updateTelephone(UmsUserSetTelephone umsUserSetTelephone);

    /**
     * 绑定手机号
     * @param umsUserSetTelephone
     * @return
     */
    int bindTelephone(UmsUserSetBindTelephone umsUserSetTelephone);
}
