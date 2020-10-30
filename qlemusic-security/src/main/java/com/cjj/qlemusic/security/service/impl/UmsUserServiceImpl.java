package com.cjj.qlemusic.security.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.cjj.qlemusic.common.exception.Asserts;
import com.cjj.qlemusic.common.util.FileUploadUtil;
import com.cjj.qlemusic.common.util.VerifyUtil;
import com.cjj.qlemusic.security.dao.UmsUserDao;
import com.cjj.qlemusic.security.entity.*;
import com.cjj.qlemusic.security.service.OssService;
import com.cjj.qlemusic.security.service.UmsUserCacheService;
import com.cjj.qlemusic.security.service.UmsUserService;
import com.cjj.qlemusic.security.util.JwtTokenUtil;
import com.cjj.qlemusic.security.util.ShiroMd5Util;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 用户管理Service实现类
 */
@Service
public class UmsUserServiceImpl implements UmsUserService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UmsUserServiceImpl.class);

    @Value("${aliyuncs.sendSms.parameters.phoneNumbersKey}")
    private String phoneNumbersKey;
    @Value("${aliyuncs.sendSms.parameters.templateParamKey}")
    private String templateParamKey;
    @Value("${aliyuncs.sendSms.verifyKey}")
    private String verifyKey;
    @Value("${aliyun.oss.host}")
    private String host;
    @Value("${user.avatar}")
    private String defaultAvatar;
    @Value("${user.uniqueId}")
    private String uniqueId;
    @Value("${user.telLoginType}")
    private String telLoginType;
    @Value("${user.credentialVerifyType}")
    private String credentialVerifyType;
    @Value("${user.credentialPasswordType}")
    private String credentialPasswordType;

    @Autowired
    private UmsUserDao umsUserDao;
    @Autowired
    private IAcsClient iAcsClient;
    @Autowired
    private CommonRequest request;
    @Autowired
    private UmsUserCacheService umsUserCacheService;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private OssService ossService;


    @Override
    public int register(UmsUserRegister umsUserRegister) {
        int count;
        UmsUser umsUser = getUserByIdentity(umsUserRegister.getTelephone());
        //判断手机号是否已经注册
        if (umsUser == null) {
            //判断两次密码是否相同
            if (umsUserRegister.getPassword().equals(umsUserRegister.getRePassword())) {
                umsUser = new UmsUser();
                //身份标识
                umsUser.setIdentity(umsUserRegister.getTelephone());
                //盐
                String salt = ShiroMd5Util.getSalt();
                umsUser.setSalt(salt);
                //密码
                umsUser.setCredential(ShiroMd5Util.getEncodePassword(umsUserRegister.getPassword(), salt));
                //设置登录类型
                umsUser.setLoginType(telLoginType);
                //设置为可用
                umsUser.setAvailable(true);
                //设置用户信息
                umsUser = setUserInfo(umsUser);
                //存入用户信息数据库
                umsUserDao.insertUserInfo(umsUser.getUmsUserInfo());
                LOGGER.info("用户信息自增id为："+umsUser.getUmsUserInfo().getId());
                //将id存入
                umsUser.getUmsUserInfo().setId(umsUser.getUmsUserInfo().getId());
                //存入用户授权信息
                umsUserDao.insertUser(umsUser);
                //创建默认收藏夹
                UmsUserCollect umsUserCollect = new UmsUserCollect();
                umsUserCollect.setName("默认");
                umsUserDao.insertCollect(umsUserCollect);
                umsUserDao.insertUserAndCollect(umsUser.getUmsUserInfo().getId(),umsUserCollect.getId());
            }else
                Asserts.fail("两次输入密码不一致");
        }else
            Asserts.fail("您已注册，请登录");
        LOGGER.info("用户注册信息为：" + JSONUtil.toJsonPrettyStr(umsUser));
        count = 1;
        return count;
    }

    @Override
    public boolean matchVerify(UmsUserLogin umsUserLogin) {
        //从Redis缓存中获取验证码
        JSONObject jsonCacheVerify = JSONUtil.parseObj(umsUserCacheService.getVerify(umsUserLogin.getTelephone()));
        Integer cacheVerify = (Integer) jsonCacheVerify.get(verifyKey);
        if (cacheVerify == null)
            throw new NullPointerException("验证码失效");
        //判断前台传来的验证码与缓存中的是否相同
        if ((umsUserLogin.getVerify()).equals(cacheVerify.toString())) {
            //验证成功后清除验证码
            umsUserCacheService.delVerify(umsUserLogin.getTelephone());
            return true;
        } else
            return false;
    }

    @Override
    public String sendSms(String telephone) throws ClientException {
        request.putQueryParameter(phoneNumbersKey, telephone);
        //随便获取6位验证码
        String verify = VerifyUtil.getJsonSixNumber();
        //存入redis（期限120s）
        umsUserCacheService.setVerify(telephone, verify);
        //调用阿里云服务
        request.putQueryParameter(templateParamKey, verify);
        //获得响应结果
        CommonResponse response = iAcsClient.getCommonResponse(request);
        return response.getData();
    }

    @Override
    public UmsUser setUserInfo(UmsUser umsUser) {
        UmsUserInfo umsUserInfo = new UmsUserInfo();
        //唯一标识ID
        umsUserInfo.setUniqueId(uniqueId+"_"+
                DateUtil.dayOfMonth(DateUtil.date())%10 +
                VerifyUtil.getSixNumber() +
                DateUtil.format(new Date(),"ss"));
        //用户头像
        umsUserInfo.setAvatar(defaultAvatar);
        //名称
        umsUserInfo.setName("ei巧了_"+ umsUser.getIdentity().substring(7));
        //注册日期
        umsUserInfo.setCreateTime(new Date());
        //手机号绑定
        umsUserInfo.setTelephone(umsUser.getIdentity());
        //用户信息
        umsUser.setUmsUserInfo(umsUserInfo);
        return umsUser;
    }

    @Override
    public String login(UmsUserLogin umsUserLogin) {
        //判断该用户是否存在
        Long id  = umsUserDao.selectUserIdByIdentity(umsUserLogin.getTelephone());
        if(id == null) Asserts.fail("用户未注册");
        //创建一个claims
        Map<String,Object> claims = new HashMap<>();
        //判断是用哪种凭证登录
        if(credentialVerifyType.equals(umsUserLogin.getCredentialType())){
            //验证码
            if(matchVerify(umsUserLogin)) claims.put(JwtTokenUtil.CLAIM_ACCOUNT,umsUserLogin.getTelephone());
            else Asserts.fail("验证码错误");
        }else {
            //密码
            //获得shiro主体
            Subject subject = SecurityUtils.getSubject();
            UsernamePasswordToken authenticationToken = new UsernamePasswordToken(umsUserLogin.getTelephone(),umsUserLogin.getPassword());
            //会到自定义的realm中认证用户
            subject.login(authenticationToken);
            //Shiro认证通过后会将user信息放到subject内，生成token并返回
            claims.put(JwtTokenUtil.CLAIM_ACCOUNT,subject.getPrincipal());
        }
        //生成token并返回
        return jwtTokenUtil.generateToken(claims);
    }

    @Override
    public UmsUser getUserByIdentity(String identity) {
        UmsUser umsUser = umsUserCacheService.getUser(identity);
        if(umsUser != null) return umsUser;
        umsUser = umsUserDao.selectUserByIdentity(identity);
        if(umsUser != null) umsUserCacheService.setUser(umsUser);
        return umsUser;
    }

    @Override
    public int updateUserInfo(UmsUserInfo umsUserInfo) {
        int count;
        //修改内容
         umsUserDao.updateUserInfo(umsUserInfo);
        //更新缓存
        if(umsUserInfo.getTelephone() != null) umsUserCacheService.delUser(umsUserInfo.getTelephone());
        if(umsUserInfo.getOauthId() != null) umsUserCacheService.delUser(umsUserInfo.getOauthId());
        count = 1;
        return count;
    }

    @Override
    public int updateUniqueId(UmsUserInfo umsUserInfo) {
        int count;
        //修改内容
        umsUserDao.updateUniqueId(umsUserInfo);
        //获取用户信息
        UmsUserInfo umsUserInfoDao = umsUserDao.selectUserInfoById(umsUserInfo.getId());
        //删除缓存
        if(umsUserInfoDao.getTelephone() != null) umsUserCacheService.delUser(umsUserInfoDao.getTelephone());
        if(umsUserInfoDao.getOauthId() != null) umsUserCacheService.delUser(umsUserInfoDao.getOauthId());
        //将修改记录插入到表unique_id中
        umsUserDao.addUpdateUniqueIdRecord(umsUserInfo.getId());
        count = 1;
        return count;
    }

    @Override
    public Long getUserByIdFromUniqueId(Long id) {
        return umsUserDao.selectUserByIdFromUniqueId(id);
    }

    @Override
    public String updateAvatar(Long id,String uniqueId,MultipartFile file) throws IOException {
        int count;
        //更改头像图片名称
        String imgName = FileUploadUtil.getTwentyBitFileName(uniqueId,file.getOriginalFilename());
        //上传到OSS
        String ossFileApiPath = ossService.uploadOss(imgName, file.getInputStream());
        //修改数据库
        umsUserDao.updateAvatar(id,host + ossFileApiPath);
        //获取用户信息
        UmsUserInfo umsUserInfoDao = umsUserDao.selectUserInfoById(id);
        //删除缓存
        if(umsUserInfoDao.getTelephone() != null) umsUserCacheService.delUser(umsUserInfoDao.getTelephone());
        if(umsUserInfoDao.getOauthId() != null) umsUserCacheService.delUser(umsUserInfoDao.getOauthId());
        count = 1;
        if(count == 1) return ossFileApiPath;
        else return null;
    }

    @Override
    public UmsUserInfo getUserById(Long id) {
        return umsUserDao.selectUserInfoById(id);
    }

}
