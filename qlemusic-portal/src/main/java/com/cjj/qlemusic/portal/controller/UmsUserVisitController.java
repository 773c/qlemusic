package com.cjj.qlemusic.portal.controller;

import com.cjj.qlemusic.common.util.ResponseResultUtil;
import com.cjj.qlemusic.portal.service.UmsUserVisitService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "用户访问量管理")
@RestController
@RequestMapping("/visit")
public class UmsUserVisitController {
    @Autowired
    private UmsUserVisitService umsUserVisitService;

    @ApiOperation(value = "访问用户数量")
    @GetMapping(value = "/usered")
    public ResponseResultUtil userVisit(@RequestParam(value = "id") Long id) {
        int count = umsUserVisitService.userVisit(id);
        if(count > 0)
            return ResponseResultUtil.success(count);
        else
            return ResponseResultUtil.failed("未知错误");
    }

    @ApiOperation(value = "获取用户数量")
    @GetMapping(value = "/getVisitCount")
    public ResponseResultUtil getVisitCount(@RequestParam(value = "id") Long id) {
        Integer visitCount = umsUserVisitService.getVisitCount(id);
        return ResponseResultUtil.success(visitCount);
    }

}
