package com.cjj.qlemusic.portal.controller;

import com.cjj.qlemusic.common.util.ResponseResultUtil;
import com.cjj.qlemusic.portal.entity.UmsUserSetBindTelephone;
import com.cjj.qlemusic.portal.entity.UmsUserSetPassword;
import com.cjj.qlemusic.portal.entity.UmsUserSetTelephone;
import com.cjj.qlemusic.portal.service.UmsUserSetService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Api(value = "用户设置管理")
@RestController
@RequestMapping("/usrset")
public class UmsUserSetController {
    @Autowired
    private UmsUserSetService umsUserSetService;

    @ApiOperation(value = "修改密码")
    @PostMapping("/updatePassword")
    public ResponseResultUtil updatePassword(@RequestBody @Validated UmsUserSetPassword umsUserSetPassword, BindingResult result){
        int count = umsUserSetService.updatePassword(umsUserSetPassword);
        if(count > 0){
            return ResponseResultUtil.success(count);
        }else {
            return ResponseResultUtil.failed("未知错误");
        }
    }

    @ApiOperation(value = "修改手机号")
    @PostMapping("/updateTelephone")
    public ResponseResultUtil updateTelephone(@RequestBody @Validated UmsUserSetTelephone umsUserSetTelephone, BindingResult result){
        try {
            int count = umsUserSetService.updateTelephone(umsUserSetTelephone);
            if(count > 0){
                return ResponseResultUtil.success(count);
            }else {
                return ResponseResultUtil.failed("未知错误");
            }
        }catch (NullPointerException e){
            return ResponseResultUtil.failed(e.getMessage());
        }
    }

    @ApiOperation(value = "修改手机号")
    @PostMapping("/bindTelephone")
    public ResponseResultUtil bindTelephone(@RequestBody @Validated UmsUserSetBindTelephone umsUserSetTelephone, BindingResult result){
        try {
            int count = umsUserSetService.bindTelephone(umsUserSetTelephone);
            if(count > 0){
                return ResponseResultUtil.success(count);
            }else {
                return ResponseResultUtil.failed("未知错误");
            }
        }catch (NullPointerException e){
            return ResponseResultUtil.failed(e.getMessage());
        }
    }

}
