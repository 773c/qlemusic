package com.cjj.qlemusic.portal.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.aliyuncs.exceptions.ClientException;
import com.cjj.qlemusic.common.util.ResponseResultUtil;
import com.cjj.qlemusic.security.entity.*;
import com.cjj.qlemusic.security.service.UmsUserService;
import com.cjj.qlemusic.security.util.JwtTokenUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


@Api(value = "用户管理")
@RestController
@RequestMapping("/user")
public class UmsUserController {
    @Value("${jwt.tokenHeader}")
    private String tokenHeader;
    @Value("${jwt.tokenHead}")
    private String tokenHead;
    @Autowired
    private UmsUserService umsUserService;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;


    @ApiOperation(value = "手机号注册")
    @PostMapping("/telRegister")
    public ResponseResultUtil telRegister(@RequestBody @Validated UmsUserRegister umsUserRegister, BindingResult result) {
        int count = umsUserService.register(umsUserRegister);
        if (count > 0)
            return ResponseResultUtil.success();
        else
            return ResponseResultUtil.failed("未知错误");
    }

    @ApiOperation(value = "匹配验证码")
    @PostMapping("/matchVerify")
    public ResponseResultUtil matchVerify(@RequestBody @Validated UmsUserLogin umsUserLogin, BindingResult result) {
        boolean isMatch = false;
        try {
            isMatch = umsUserService.matchVerify(umsUserLogin);
        } catch (NullPointerException e) {
            return ResponseResultUtil.verifyCodeFailed(e.getMessage());
        }
        if (isMatch)
            return ResponseResultUtil.success();
        else
            return ResponseResultUtil.verifyCodeFailed();
    }

    @ApiOperation(value = "发送短信")
    @PostMapping("/sendSms")
    public ResponseResultUtil sendSms(String telephone) {
        String data = null;
        try {
            data = umsUserService.sendSms(telephone);
        } catch (ClientException e) {
            e.printStackTrace();
        }
        //转为JSON格式
        JSONObject dataJson = JSONUtil.parseObj(data);
        //判断号码是否正确
        if (dataJson.get("Message").equals("OK")) {
            return ResponseResultUtil.success(data);
        } else {
            return ResponseResultUtil.failed("您输入的手机号码格式有误");
        }
    }

    @ApiOperation(value = "手机，邮箱账号密码或手机验证码登录")
    @PostMapping("/login")
    public ResponseResultUtil login(@RequestBody @Validated UmsUserLogin umsUserLogin, BindingResult result) {
        try {
            String token = umsUserService.login(umsUserLogin);
            return ResponseResultUtil.success(token);
        }catch (AuthenticationException e){
            return ResponseResultUtil.validateFailed("账号或密码错误");
        }catch (NullPointerException e){
            e.printStackTrace();
            return ResponseResultUtil.validateFailed(e.getMessage());
        }

    }

    @ApiOperation(value = "获取用户信息")
    @RequestMapping("/info")
    public ResponseResultUtil getUserInfo(HttpServletRequest request){
        System.out.println("❥❥❥❥❥❥❥❥❥❥❥❥❥❥❥❥ 获取用户信息");
        String token = request.getHeader(tokenHeader).substring(tokenHead.length() + 1);
        String identity = jwtTokenUtil.getAccountFromToken(token);
        Map<String,Object> mapInfo = new HashMap<>();
        //像这种频繁访问的数据可以放入redis缓存中
        UmsUser umsUser = umsUserService.getUserByIdentity(identity);
        System.out.println("umsUser："+JSONUtil.toJsonPrettyStr(umsUser));
        //存入用户信息到map中
        mapInfo.put("id", umsUser.getUmsUserInfo().getId());
        mapInfo.put("uniqueId", umsUser.getUmsUserInfo().getUniqueId());
        mapInfo.put("name", umsUser.getUmsUserInfo().getName());
        mapInfo.put("sex", umsUser.getUmsUserInfo().getSex());
        mapInfo.put("avatar", umsUser.getUmsUserInfo().getAvatar());
        mapInfo.put("createTime", DateUtil.formatDateTime(umsUser.getUmsUserInfo().getCreateTime()));
        mapInfo.put("description", umsUser.getUmsUserInfo().getDescription());
        mapInfo.put("telephone", umsUser.getUmsUserInfo().getTelephone());
        mapInfo.put("email", umsUser.getUmsUserInfo().getEmail());
        mapInfo.put("oauthId", umsUser.getUmsUserInfo().getOauthId());
        return ResponseResultUtil.success(mapInfo);
    }

    @ApiOperation(value = "修改用户信息")
    @PostMapping(value = "/updateInfo")
    public ResponseResultUtil updateUserInfo(@RequestBody UmsUserInfo umsUserInfo) {
        int count = umsUserService.updateUserInfo(umsUserInfo);
        if(count > 0)
            return ResponseResultUtil.success(count);
        else
            return ResponseResultUtil.failed("修改失败");
    }

    @ApiOperation(value = "修改用户唯一ID")
    @PostMapping(value = "/updateUniqueId")
    public ResponseResultUtil updateUniqueId(@RequestBody @Validated UmsUserInfo umsUserInfo,BindingResult result) {
        int count = umsUserService.updateUniqueId(umsUserInfo);
        if(count > 0)
            return ResponseResultUtil.success(count);
        else
            return ResponseResultUtil.failed("修改失败");
    }

    @ApiOperation(value = "查询是否修改过唯一ID")
    @GetMapping(value = "/isUpdateUniqueId")
    public ResponseResultUtil isUpdateUniqueId(@RequestParam(value = "id") Long id) {
        Long userByIdFromUniqueId = umsUserService.getUserByIdFromUniqueId(id);
        if(userByIdFromUniqueId != null)
            return ResponseResultUtil.failed("修改次数已上限，请勿重复操作");
        else
            return ResponseResultUtil.success();
    }

    @ApiOperation(value = "修改头像")
    @PostMapping(value = "/updateAvatar",headers = "content-type=multipart/form-data")
    public ResponseResultUtil updateAvatar(@RequestParam(value = "id") Long id,
                                           @RequestParam(value = "uniqueId") String uniqueId,
                                           @RequestParam(value = "file") MultipartFile file) {
        String ossFileApiPath = null;
        try {
            ossFileApiPath = umsUserService.updateAvatar(id,uniqueId,file);
        }catch (IOException e){
            e.printStackTrace();
            return ResponseResultUtil.failed("修改失败");
        }
        if(ossFileApiPath != null)
            return ResponseResultUtil.success(ossFileApiPath);
        else
            return ResponseResultUtil.failed("修改失败");
    }

    @ApiOperation(value = "查询用户")
    @GetMapping(value = "/getUserById")
    public ResponseResultUtil getUserById(@RequestParam(value = "id") Long id) {
        UmsUserInfo user = umsUserService.getUserById(id);
        if(user != null)
            return ResponseResultUtil.success(user);
        else
            return ResponseResultUtil.failed("您查看的用户不存在");
    }

    @ApiOperation(value = "退出系统")
    @PostMapping(value = "/logout")
    public ResponseResultUtil logout() {
        Subject subject= SecurityUtils.getSubject();
        subject.logout();
        return ResponseResultUtil.success();
    }
}
