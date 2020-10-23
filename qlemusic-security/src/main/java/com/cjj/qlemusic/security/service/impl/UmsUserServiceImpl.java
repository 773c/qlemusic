package com.cjj.qlemusic.security.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.cjj.qlemusic.common.exception.Asserts;
import com.cjj.qlemusic.common.util.FileUploadUtil;
import com.cjj.qlemusic.common.util.TelephoneUtil;
import com.cjj.qlemusic.common.util.VerifyUtil;
import com.cjj.qlemusic.security.dao.UmsCollectDao;
import com.cjj.qlemusic.security.dao.UmsUserDao;
import com.cjj.qlemusic.security.entity.UmsCollect;
import com.cjj.qlemusic.security.entity.UmsUser;
import com.cjj.qlemusic.security.entity.UmsUserRegister;
import com.cjj.qlemusic.security.service.OssService;
import com.cjj.qlemusic.security.service.UmsUserCacheService;
import com.cjj.qlemusic.security.service.UmsUserService;
import com.cjj.qlemusic.security.util.JwtTokenUtil;
import com.cjj.qlemusic.security.util.ShiroMd5Util;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
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
    @Value("${aliyuncs.sendSms.parameters.phoneNumbersKey}")
    private String phoneNumbersKey;
    @Value("${aliyuncs.sendSms.parameters.templateParamKey}")
    private String templateParamKey;
    @Value("${aliyuncs.sendSms.verifyKey}")
    private String verifyKey;
    @Value("${user.avatar}")
    private String defaultAvatar;
    @Value("${user.uniqueId}")
    private String uniqueId;
    @Value("${aliyun.oss.host}")
    private String host;

    @Autowired
    private UmsUserDao userDao;
    @Autowired
    private IAcsClient iAcsClient;
    @Autowired
    private CommonRequest request;
    @Autowired
    private UmsUserCacheService userCacheService;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private OssService ossService;
    @Autowired
    private UmsCollectDao collectDao;

    @Override
    public UmsUser register(UmsUserRegister umsUserRegister) {
        int count = 0;
        UmsUser regUser = null;
        UmsUser user = getUserByTelephone(umsUserRegister.getTelephone());
        //判断手机号是否已经注册
        if (user == null) {
            //判断两次密码是否相同
            if (umsUserRegister.getPassword().equals(umsUserRegister.getRePassword())) {
                regUser = new UmsUser();
                //手机号
                regUser.setTelephone(umsUserRegister.getTelephone());
                //盐
                String salt = ShiroMd5Util.getSalt();
                regUser.setSalt(salt);
                //密码
                regUser.setPassword(ShiroMd5Util.getEncodePassword(umsUserRegister.getPassword(), salt));
                //设置用户信息
                regUser = setUserInfo(regUser);
                //存入数据库
                userDao.insert(regUser);
                //这里进行收藏夹默认的创建
                addDefaultCollect(regUser);
                count = 1;
            }
            if(count == 1)
                return regUser;
            else
                return null;
        }
        user.setAvailable(false);
        return user;
    }

    @Override
    public boolean matchVerify(UmsUser umsUser) {
        //从Redis缓存中获取验证码
        JSONObject jsonCacheVerify = JSONUtil.parseObj(userCacheService.getVerify(umsUser.getTelephone()));
        Integer cacheVerify = (Integer) jsonCacheVerify.get(verifyKey);
        if (cacheVerify == null)
            throw new NullPointerException("验证码失效");
        //判断前台传来的验证码与缓存中的是否相同
        if ((umsUser.getVerify()).equals(cacheVerify.toString())) {
            //验证成功后清除验证码
            userCacheService.delVerify(umsUser.getTelephone());
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
        userCacheService.setVerify(telephone, verify);
        //调用阿里云服务
        request.putQueryParameter(templateParamKey, verify);
        //获得响应结果
        CommonResponse response = iAcsClient.getCommonResponse(request);
        return response.getData();
    }

    @Override
    public UmsUser setUserInfo(UmsUser umsUser) {
        //存入唯一标识ID
        umsUser.setUniqueId(uniqueId+"_"+
                DateUtil.dayOfMonth(DateUtil.date())%10 +
                VerifyUtil.getJsonSixNumber() +
                DateUtil.format(new Date(),"ss"));
        //存入用户头像
        umsUser.setHeadIcon(defaultAvatar);
        //存入名称
        umsUser.setName(TelephoneUtil.encryptTelephone(umsUser.getTelephone()));
        //存入注册日期
        umsUser.setCreateTime(new Date());
        //是否可用
        umsUser.setAvailable(true);
        return umsUser;
    }

    @Override
    public String login(UmsUser umsUser) {
        //判断该用户是否存在
        UmsUser user = getUserByTelephone(umsUser.getTelephone());
        if(user == null)
            //抛出自定义异常
            Asserts.fail("用户未注册");
        //创建一个claims
        Map<String,Object> claims = new HashMap<>();
        //判断是验证码登录还是密码
        if(StrUtil.isEmpty(umsUser.getVerify())){
            //密码登录
            //获取主体
            Subject subject = SecurityUtils.getSubject();
            UsernamePasswordToken authenticationToken = new UsernamePasswordToken(umsUser.getTelephone(),umsUser.getPassword());
            //会到自定义的realm中认证用户
            subject.login(authenticationToken);
            //Shiro认证通过后会将user信息放到subject内，生成token并返回
            claims.put(JwtTokenUtil.CLAIM_ACCOUNT,subject.getPrincipal());
        }else {
            //验证码登录
            if(matchVerify(umsUser))
                //验证码认证成功后将user信息放到subject内
                claims.put(JwtTokenUtil.CLAIM_ACCOUNT,umsUser.getTelephone());
            else
                return null;
        }
        //生成token并返回
        return jwtTokenUtil.generateToken(claims);
    }
    @Override
    public UmsUser getUserByTelephone(String telephone) {
        UmsUser user = userCacheService.getUser(telephone);
        if(user != null) return user;
        System.out.println("redis缓存中不存在，正在访问数据库根据账号查询是否存在用户。。。。");
        user = userDao.selectUserByTelephone(telephone);
        if(user != null)
            userCacheService.setUser(user);
        return user;
    }

    @Override
    public int updateInfo(UmsUser umsUser) {
        //修改内容
        int count = userDao.updateInfo(umsUser);
        //更新缓存
        userCacheService.delUser(umsUser.getTelephone());
        userCacheService.setUser(userDao.selectUserById(umsUser.getId()));
        return count;
    }

    @Override
    public int updateUniqueId(Long id,String uniqueId) {
        int count;
        //修改内容
        userDao.updateUniqueId(id,uniqueId);
        //更新缓存
        userCacheService.delUser(userDao.selectUserById(id).getTelephone());
        userCacheService.setUser(userDao.selectUserById(id));
        //将修改记录插入到表unique_id中
        userDao.addUpdateUniqueIdRecord(id);
        count = 1;
        return count;
    }

    @Override
    public Long getUserByIdFromUniqueId(Long id) {
        return userDao.selectUserByIdFromUniqueId(id);
    }

    @Override
    public String updateAvatar(Long id,String uniqueId,MultipartFile file) throws IOException {
        int count;
        //更改头像图片名称
        String imgName = FileUploadUtil.getTwentyBitFileName(uniqueId,file.getOriginalFilename());
        //上传到OSS
        String ossFileApiPath = ossService.uploadOss(imgName, file.getInputStream());
        //修改数据库
        userDao.updateAvatar(id,host + ossFileApiPath);
        //更新缓存
        userCacheService.delUser(userDao.selectUserById(id).getTelephone());
        userCacheService.setUser(userDao.selectUserById(id));
        count = 1;
        if(count == 1)
            return ossFileApiPath;
        else
            return null;
    }

    @Override
    public void addDefaultCollect(UmsUser regUser) {
        UmsCollect umsCollect = new UmsCollect();
        umsCollect.setName("默认");
        collectDao.createCollect(umsCollect);
        collectDao.insertUserAndCollect(regUser.getId(),umsCollect.getId());
    }

    @Override
    public UmsUser getUserById(Long id) {
        return userDao.selectUserById(id);
    }

    @Override
    public void delUserInfoCache(String telephone) {
        userCacheService.delUser(telephone);
    }

}
