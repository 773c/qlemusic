package com.cjj.qlemusic.portal.controller;

import com.cjj.qlemusic.common.util.ResponseResultUtil;
import com.cjj.qlemusic.common.util.TelephoneUtil;
import com.cjj.qlemusic.portal.entity.BbsMusic;
import com.cjj.qlemusic.portal.service.BbsMusicService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(value = "音乐片段管理")
@RestController
@RequestMapping("/audio")
public class BbsMusicController {
    @Autowired
    private BbsMusicService bbsMusicService;

    @ApiOperation(value = "获取推荐音乐片段")
    @GetMapping("/recommend")
    public ResponseResultUtil recommend(){
        System.out.println("获取推荐音乐片段❥❥❥❥❥❥❥❥❥❥❥❥❥❥❥❥");
        System.out.println(TelephoneUtil.encryptTelephone("15160609283"));
        List<BbsMusic> recommendList = bbsMusicService.getRecommendList();
        return ResponseResultUtil.success(recommendList);
    }
}
