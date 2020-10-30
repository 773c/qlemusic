package com.cjj.qlemusic.portal.service.impl;

import com.cjj.qlemusic.common.exception.Asserts;
import com.cjj.qlemusic.portal.dao.UmsUserSetDao;
import com.cjj.qlemusic.portal.entity.UmsUserSetBindTelephone;
import com.cjj.qlemusic.portal.entity.UmsUserSetPassword;
import com.cjj.qlemusic.portal.entity.UmsUserSetTelephone;
import com.cjj.qlemusic.portal.service.UmsUserSetService;
import com.cjj.qlemusic.security.entity.UmsUser;
import com.cjj.qlemusic.security.entity.UmsUserInfo;
import com.cjj.qlemusic.security.entity.UmsUserLogin;
import com.cjj.qlemusic.security.service.UmsUserCacheService;
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
    @Autowired
    private UmsUserCacheService umsUserCacheService;

    @Override
    public int updatePassword(UmsUserSetPassword umsUserSetPassword) {
        int count;
        UmsUserInfo umsUserInfo = umsUserSetDao.selectUserInfoById(umsUserSetPassword.getUserId());
        System.out.println("=======>"+umsUserInfo);
        //判断用户是否为手机用户
        if(umsUserInfo.getOauthId() != null){
            //不是，直接返回
            Asserts.fail("请绑定手机号");
        }
        //判断用户旧密码是否相等
        UmsUser umsUser = umsUserSetDao.selectUserById(umsUserSetPassword.getUserId());
        String oldPassword = ShiroMd5Util.getEncodePassword(umsUserSetPassword.getOldPassword(), umsUser.getSalt());
        if(!umsUser.getCredential().equals(oldPassword)){
            //不相等直接返回
            Asserts.fail("用户密码错误");
        }else {
            //相等，可以修改密码
            String salt = ShiroMd5Util.getSalt();
            String newPassword = ShiroMd5Util.getEncodePassword(umsUserSetPassword.getNewPassword(), salt);
            umsUserSetPassword.setSalt(salt);
            umsUserSetPassword.setNewPassword(newPassword);
            umsUserSetDao.updatePassword(umsUserSetPassword);
            //清除用户信息缓存
            umsUserCacheService.delUser(umsUserInfo.getTelephone());
        }
        count = 1;
        return count;
    }

    @Override
    public int updateTelephone(UmsUserSetTelephone umsUserSetTelephone) {
        int count;
        UmsUserLogin umsUserLogin = new UmsUserLogin();
        umsUserLogin.setTelephone(umsUserSetTelephone.getNewTelephone());
        umsUserLogin.setVerify(umsUserSetTelephone.getSetVerify());
        if(!umsUserService.matchVerify(umsUserLogin)){
            //如果验证不成功，直接返回
            Asserts.fail("验证码错误");
        }else {
            Long userId = umsUserSetDao.selectUserByNewTelephone(umsUserSetTelephone.getNewTelephone());
            //判断手机号是否已经被绑定
            if(userId != null){
                //如何更换的新手机与原来相同，则直接返回
                if(userId.equals(umsUserSetTelephone.getUserId()))
                    Asserts.fail("不能与原来绑定的手机一致");
                else
                    Asserts.fail("该手机号已被绑定");
            } else{
                //没有则直接更新
                umsUserSetDao.updateTelephone(umsUserSetTelephone);
                umsUserSetDao.updateUserInfoToTelephone(umsUserSetTelephone);
                //清除用户信息缓存
                umsUserCacheService.delUser(umsUserLogin.getTelephone());
            }
        }
        count = 1;
        return count;
    }

    @Override
    public int bindTelephone(UmsUserSetBindTelephone umsUserSetTelephone) {
        int count;
//        UmsUserLogin umsUserLogin = new UmsUserLogin();
//        umsUserLogin.setTelephone(umsUserSetTelephone.getTelephone());
//        umsUserLogin.setVerify(umsUserSetTelephone.getSetVerify());
//        if(!umsUserService.matchVerify(umsUserLogin)){
//            //如果验证不成功，直接返回
//            Asserts.fail("验证码错误");
//        }else {
//            Long userId = umsUserSetDao.selectUserByNewTelephone(umsUserSetTelephone.getTelephone());
//            //判断手机号是否已经被绑定
//            if(userId != null){
//                //有则判断该手机号是否有绑定第三方账号
//                int num = umsUserSetDao.selectUserCountByUserId(userId);
//                if(num == 1){
//
//                }
//                Asserts.fail("该手机号已被绑定");
//            } else{
//                //没有则直接更新
//                umsUserSetDao.updateTelephone(umsUserSetTelephone);
//                umsUserSetDao.updateUserInfoToTelephone(umsUserSetTelephone);
//                //清除用户信息缓存
//                umsUserCacheService.delUser(umsUserLogin.getTelephone());
//            }
//        }
        count = 1;
        return count;
    }
}
