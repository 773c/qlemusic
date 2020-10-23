package com.cjj.qlemusic.portal.service.impl;

import com.cjj.qlemusic.common.exception.Asserts;
import com.cjj.qlemusic.portal.dao.UmsUserSetDao;
import com.cjj.qlemusic.portal.entity.UmsUserSetPassword;
import com.cjj.qlemusic.portal.entity.UmsUserSetTelephone;
import com.cjj.qlemusic.portal.service.UmsUserSetService;
import com.cjj.qlemusic.security.entity.UmsUser;
import com.cjj.qlemusic.security.service.UmsUserService;
import com.cjj.qlemusic.security.util.ShiroMd5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 用户设置Service实现类
 */
@Service
public class UmsUserSetServiceImpl implements UmsUserSetService {
    @Autowired
    private UmsUserSetDao umsUserSetDao;
    @Autowired
    private UmsUserService umsUserService;

    @Override
    public int updatePassword(UmsUserSetPassword umsUserSetPassword) {
        int count;
        //判断用户旧密码是否相等
        UmsUser umsUser = umsUserSetDao.selectUserById(umsUserSetPassword.getUserId());
        String oldPassword = ShiroMd5Util.getEncodePassword(umsUserSetPassword.getOldPassword(), umsUser.getSalt());
        if(!umsUser.getPassword().equals(oldPassword)){
            //不相等直接返回
            Asserts.fail("用户密码错误");
        }else {
            //相等，可以修改密码
            String salt = ShiroMd5Util.getSalt();
            String newPassword = ShiroMd5Util.getEncodePassword(umsUserSetPassword.getNewPassword(), salt);
            umsUserSetPassword.setSalt(salt);
            umsUserSetPassword.setNewPassword(newPassword);
            umsUserSetDao.updatePassword(umsUserSetPassword);
            System.out.println(umsUser.getTelephone());
            //清除用户信息缓存
            umsUserService.delUserInfoCache(umsUser.getTelephone());
        }
        count = 1;
        return count;
    }

    @Override
    public int updateTelephone(UmsUserSetTelephone umsUserSetTelephone) {
        int count;
        UmsUser umsUser = new UmsUser();
        umsUser.setTelephone(umsUserSetTelephone.getNewTelephone());
        umsUser.setVerify(umsUserSetTelephone.getSetVerify());
        System.out.println(umsUser);
        if(!umsUserService.matchVerify(umsUser)){
            //如果验证不成功，直接返回
            Asserts.fail("验证码错误");
        }else {
            Long userId = umsUserSetDao.selectUserByNewTelephone(umsUserSetTelephone.getNewTelephone());
            if(userId != null){
                //如何更换的新手机与原来相同，则直接返回
                if(userId.equals(umsUserSetTelephone.getUserId()))
                    Asserts.fail("不能与原来绑定的手机一致");
                else
                    Asserts.fail("该手机号已被绑定");
            } else{
                //否则，直接更新
                umsUserSetDao.updateTelephone(umsUserSetTelephone);
                //清除用户信息缓存
                umsUserService.delUserInfoCache(umsUserSetTelephone.getOldTelephone());
            }
        }
        count = 1;
        return count;
    }
}
