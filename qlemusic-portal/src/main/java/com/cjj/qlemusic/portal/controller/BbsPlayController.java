package com.cjj.qlemusic.portal.controller;

import com.cjj.qlemusic.common.util.ResponseResultUtil;
import com.cjj.qlemusic.portal.service.BbsPlayService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "播放量管理")
@RestController
@RequestMapping("/play")
public class BbsPlayController {
    @Autowired
    private BbsPlayService bbsPlayService;

    @ApiOperation(value = "播放量")
    @GetMapping("/bbsmusic")
    public ResponseResultUtil playBbsMusic(@RequestParam(value = "musicId") Long musicId) {
        int count = bbsPlayService.playBbsMusic(musicId);
        if(count > 0){
            return ResponseResultUtil.success();
        }else {
            return ResponseResultUtil.failed("未知错误");
        }

    }
}
