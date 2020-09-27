package com.cjj.qlemusic.admin.controller;


import com.cjj.qlemusic.common.util.ResponseResultUtil;
import com.cjj.qlemusic.security.entity.UmsAdmin;
import com.cjj.qlemusic.security.entity.UmsAdminLogin;
import com.cjj.qlemusic.security.service.UmsAdminCacheService;
import com.cjj.qlemusic.security.service.UmsAdminService;
import com.cjj.qlemusic.security.service.UmsRoleService;
import com.cjj.qlemusic.security.util.JwtTokenUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;


/**
 * 管理员用户Controller
 */

@Api(tags = "UmsAdminController", description = "管理员用户管理")
@RestController
@RequestMapping("/admin")
public class UmsAdminController {
    @Value("${jwt.tokenHeader}")
    private String tokenHeader;
    @Value("${jwt.tokenHead}")
    private String tokenHead;
    @Autowired
    private UmsAdminService umsAdminService;
    @Autowired
    private UmsAdminCacheService umsAdminCacheService;
    @Autowired
    private UmsRoleService umsRoleService;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;



    @RequestMapping("/test")
    public ResponseResultUtil test() {
        System.out.println("shiro测试");
        return ResponseResultUtil.success("shiro测试");
    }

    @RequiresPermissions("user:update")
    @RequestMapping("/test2")
    public ResponseResultUtil test2() {
        System.out.println("shiro测试2");
        return ResponseResultUtil.success("shiro测试2");
    }
    @RequestMapping("/test3")
    public ResponseResultUtil test3() {
        System.out.println("shiro测试3");
        return ResponseResultUtil.success("shiro测试3");
    }

    @RequestMapping("/addTest")
    public String addTest() {
        UmsAdmin umsAdmin = new UmsAdmin();
        umsAdmin.setAccount("aaa");
        umsAdmin.setName("777");
        umsAdmin.setPassword("1234");
        umsAdminService.addTest(umsAdmin);
        return "shiro addTest";
    }

    @ApiOperation("管理员登录后返回token")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseResultUtil login(@Validated @RequestBody UmsAdminLogin umsAdminLogin, BindingResult result) {
        System.out.println("用户："+umsAdminLogin+"正在登录");
        //判断用户在缓存中是否存在
        if(umsAdminCacheService.getAdmin(umsAdminLogin.getAccount())!=null){
            System.out.println("存在该用户");
        }
        try {
            String token = umsAdminService.login(umsAdminLogin);
            return ResponseResultUtil.success(token);
        } catch (AuthenticationException e) {
            return ResponseResultUtil.validateFailed("账号或密码错误");
        }
    }
    @ApiOperation("获取当前管理员用户信息")
    @RequestMapping("/info")
    public ResponseResultUtil getAdminInfo(HttpServletRequest request) {
        System.out.println("获取管理员用户信息");
        //获取token
        String token = request.getHeader(tokenHeader).substring(tokenHead.length()+1);
        //获取管理员账号
        String account = jwtTokenUtil.getAccountFromToken(token);
        UmsAdmin umsAdmin = umsAdminService.getAdminByAccount(account);
        Map<String,Object> data = new HashMap<>();
        data.put("name",umsAdmin.getName());
        data.put("account",umsAdmin.getAccount());
        data.put("roles",umsRoleService.getRoleByAdminId(umsAdmin.getId()));
        data.put("menus",umsRoleService.getMenuList(umsAdmin.getId()));
        data.put("headIcon",umsAdmin.getHeadIcon());

        return ResponseResultUtil.success(data);
    }

    @ApiOperation(value = "退出系统")
    @RequestMapping(value = "/logout",method = RequestMethod.POST)
    public ResponseResultUtil logout() {
        Subject subject= SecurityUtils.getSubject();
        subject.logout();
        return ResponseResultUtil.success();
    }

}
