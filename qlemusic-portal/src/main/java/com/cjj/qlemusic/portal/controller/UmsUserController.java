package com.cjj.qlemusic.portal.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.aliyuncs.exceptions.ClientException;
import com.cjj.qlemusic.common.util.ResponseResultUtil;
import com.cjj.qlemusic.security.entity.UmsUser;
import com.cjj.qlemusic.security.entity.UmsUserRegister;
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
@RequestMapping("/portal")
public class UmsUserController {
    @Value("${jwt.tokenHeader}")
    private String tokenHeader;
    @Value("${jwt.tokenHead}")
    private String tokenHead;
    @Autowired
    private UmsUserService userService;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @ApiOperation(value = "qq授权")
    @RequestMapping("/connectqq")
    public String connectqq(HttpServletRequest request) {
        String accessToken = request.getParameter("access_token");
        return accessToken;
    }



    @ApiOperation(value = "手机号注册")
    @PostMapping("/telRegister")
    public ResponseResultUtil telRegister(@RequestBody @Validated UmsUserRegister umsUserRegister, BindingResult result) {
        System.out.println(umsUserRegister);
        UmsUser regUser = userService.register(umsUserRegister);
        if (regUser == null)
            return ResponseResultUtil.failed();
        else if (regUser.getAvailable() == true)
            return ResponseResultUtil.success();
        else
            return ResponseResultUtil.failed("账号已注册，请直接登录");
    }

    @ApiOperation(value = "匹配验证码")
    @PostMapping("/matchVerify")
    public ResponseResultUtil matchVerify(@RequestBody @Validated UmsUser umsUser, BindingResult result) {
        System.out.println(umsUser);
        boolean isMatch = false;
        try {
            isMatch = userService.matchVerify(umsUser);
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
    public ResponseResultUtil sendSms(@RequestParam(value = "telephone") String telephone) {
        System.out.println(telephone);
        String data = null;
        try {
            data = userService.sendSms(telephone);
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

    @ApiOperation(value = "密码或验证码登录")
    @PostMapping("/login")
    public ResponseResultUtil login(@RequestBody @Validated UmsUser umsUser, BindingResult result) {
        try {
            String token = userService.login(umsUser);
            if(token != null)
                return ResponseResultUtil.success(token);
            else
                return ResponseResultUtil.failed("验证码错误");
        }catch (AuthenticationException e){
            return ResponseResultUtil.validateFailed("账号或密码错误");
        }catch (NullPointerException e){
            return ResponseResultUtil.validateFailed(e.getMessage());
        }
    }

    @ApiOperation(value = "获取用户信息")
    @RequestMapping("/info")
    public ResponseResultUtil getUserInfo(HttpServletRequest request){
        System.out.println("获取用户信息");

        String token = request.getHeader(tokenHeader).substring(tokenHead.length() + 1);
        String telephone = jwtTokenUtil.getAccountFromToken(token);
        //像这种频繁访问的数据可以放入redis缓存中
        UmsUser umsUser = userService.getUserByTelephone(telephone);
        System.out.println(umsUser);
        Map<String,Object> mapInfo = new HashMap<>();
        mapInfo.put("id",umsUser.getId());
        mapInfo.put("uniqueId",umsUser.getUniqueId());
        mapInfo.put("name",umsUser.getName());
        mapInfo.put("sex",umsUser.getSex());
        mapInfo.put("telephone",umsUser.getTelephone());
        mapInfo.put("createTime", DateUtil.formatDateTime(umsUser.getCreateTime()));
        mapInfo.put("email",umsUser.getEmail());
        mapInfo.put("description",umsUser.getDescription());
        mapInfo.put("headIcon",umsUser.getHeadIcon());

        return ResponseResultUtil.success(mapInfo);
    }

    @ApiOperation(value = "修改用户信息")
    @PostMapping(value = "/updateInfo")
    public ResponseResultUtil updateInfo(@RequestBody UmsUser umsUser) {
        System.out.println("修改用户信息："+umsUser);
        int count = userService.updateInfo(umsUser);
        if(count > 0)
            return ResponseResultUtil.success(count);
        else
            return ResponseResultUtil.failed("修改用户信息失败");
    }

    @ApiOperation(value = "修改用户唯一ID")
    @GetMapping(value = "/updateUniqueId")
    public ResponseResultUtil updateUniqueId(@RequestParam(value = "id") Long id,
                                             @RequestParam(value = "uniqueId") String uniqueId) {
        int count = userService.updateUniqueId(id,uniqueId);
        if(count > 0)
            return ResponseResultUtil.success(count);
        else
            return ResponseResultUtil.failed("修改唯一ID失败");
    }

    @ApiOperation(value = "查询是否修改过唯一ID")
    @GetMapping(value = "/isUpdateUniqueId")
    public ResponseResultUtil isUpdateUniqueId(@RequestParam(value = "id") Long id) {
        Long userByIdFromUniqueId = userService.getUserByIdFromUniqueId(id);
        if(userByIdFromUniqueId != null)
            return ResponseResultUtil.failed("您的ID已修改，请勿重复操作");
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
            ossFileApiPath = userService.updateAvatar(id,uniqueId,file);
        }catch (IOException e){
            return ResponseResultUtil.failed("修改头像失败,IO异常");
        }
        if(ossFileApiPath != null)
            return ResponseResultUtil.success(ossFileApiPath);
        else
            return ResponseResultUtil.failed("修改头像失败");
    }

    @ApiOperation(value = "查询用户")
    @GetMapping(value = "/getUserById")
    public ResponseResultUtil getUserById(@RequestParam(value = "id") Long id) {
        UmsUser user = userService.getUserById(id);
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
