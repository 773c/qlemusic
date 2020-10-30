package com.cjj.qlemusic.portal.controller;

import com.cjj.qlemusic.common.util.ResponseResultUtil;
import com.cjj.qlemusic.security.service.OauthService;
import com.cjj.qlemusic.security.service.UmsUserOauthService;
import com.qq.connect.QQConnectException;
import com.qq.connect.javabeans.AccessToken;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Api(value = "oauth2.0授权管理")
@RestController
@RequestMapping("/oauth")
public class UmsUserOauthController {

    @Autowired
    private OauthService oauthService;
    @Autowired
    private UmsUserOauthService umsUserOauthService;

    @ApiOperation(value = "qq登录跳转页")
    @PostMapping("/qqLogin")
    public ResponseResultUtil qqLogin(HttpServletRequest request) throws QQConnectException {
        return ResponseResultUtil.success(oauthService.getAuthorizaUrl(request));
    }

    @ApiOperation(value = "qq授权成功")
    @GetMapping("/qqLoginAuthSuccess")
    public ResponseResultUtil qqLoginAuthSuccess(HttpServletRequest request) throws QQConnectException {
        System.out.println("❥❥❥❥❥❥❥❥❥❥❥❥❥❥❥❥ qq授权成功");
        String state = request.getParameter("state");
        request.getSession().setAttribute("qq_connect_state",state);
        AccessToken accessToken = oauthService.getAccessToken(request);
        String token = umsUserOauthService.saveQQUserInfo(accessToken);
        return ResponseResultUtil.success(token);
    }
}
