package com.cjj.qlemusic.portal.controller;

import com.cjj.qlemusic.common.util.PageUtil;
import com.cjj.qlemusic.common.util.ResponseResultUtil;
import com.cjj.qlemusic.portal.entity.BbsMusic;
import com.cjj.qlemusic.portal.service.BbsMusicService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
        List<BbsMusic> recommendList = bbsMusicService.getRecommendList();
        return ResponseResultUtil.success(recommendList);
    }

    @ApiOperation(value = "获取我的音乐片段")
    @GetMapping("/myMusic")
    public ResponseResultUtil myMusic(@RequestParam(value = "id") Long id){
        System.out.println("获取我的音乐片段❥❥❥❥❥❥❥❥❥❥❥❥❥❥❥❥");
        List<BbsMusic> myMusicList = bbsMusicService.getMyMusicList(id);
        return ResponseResultUtil.success(myMusicList);
    }

    @ApiOperation(value = "获取相应收藏夹的内容")
    @GetMapping(value = "/getMusicByCollectId")
    public ResponseResultUtil getMusicByCollectId(@RequestParam(value = "pageNum",defaultValue = "1") Integer pageNum,
                                                  @RequestParam(value = "pageSize",defaultValue = "10") Integer pageSize,
                                                  @RequestParam(value = "userId")Long userId,
                                                  @RequestParam(value = "collectId")Long collectId) {
        List<BbsMusic> collectContentList = bbsMusicService.getMusicByCollectId(pageNum,pageSize,userId,collectId);
        return ResponseResultUtil.success(PageUtil.restPage(collectContentList));
    }

}
