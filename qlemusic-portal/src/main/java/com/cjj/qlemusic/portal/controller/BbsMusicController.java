package com.cjj.qlemusic.portal.controller;

import com.cjj.qlemusic.common.util.PageUtil;
import com.cjj.qlemusic.common.util.ResponseResultUtil;
import com.cjj.qlemusic.portal.entity.BbsMusic;
import com.cjj.qlemusic.portal.service.BbsMusicService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Api(value = "音乐片段管理")
@RestController
@RequestMapping("/audio")
@Validated
public class BbsMusicController {
    @Autowired
    private BbsMusicService bbsMusicService;

    @ApiOperation(value = "获取推荐音乐片段")
    @GetMapping("/recommend")
    public ResponseResultUtil recommend(@RequestParam(value = "pageNum",defaultValue = "1") Integer pageNum,
                                        @RequestParam(value = "pageSize",defaultValue = "10") Integer pageSize){
        System.out.println("获取推荐音乐片段❥❥❥❥❥❥❥❥❥❥❥❥❥❥❥❥");
        List<BbsMusic> recommendList = null;
        try {
            recommendList = bbsMusicService.getRecommendList(pageNum,pageSize);
            return ResponseResultUtil.success(PageUtil.restPage(recommendList));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseResultUtil.failed("未知错误");
        }
    }

    @ApiOperation(value = "获取我的音乐片段")
    @GetMapping("/myMusic")
    public ResponseResultUtil myMusic(@RequestParam(value = "pageNum",defaultValue = "1") Integer pageNum,
                                      @RequestParam(value = "pageSize",defaultValue = "10") Integer pageSize,
                                      @RequestParam(value = "userId") Long userId,String category){
        System.out.println("获取我的音乐片段❥❥❥❥❥❥❥❥❥❥❥❥❥❥❥❥");
        List<BbsMusic> myMusicList = bbsMusicService.getMyMusicList(pageNum,pageSize,userId,category);
        return ResponseResultUtil.success(PageUtil.restPage(myMusicList));
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

    @ApiOperation(value = "发布音乐")
    @PostMapping(value = "/release",headers = "content-type=multipart/form-data")
    public ResponseResultUtil release(
            BbsMusic bbsMusic,
            @RequestParam(value = "userId")
            @NotNull(message = "用户ID不能为空") Long userId,
            @RequestParam(value = "file")
            @NotNull(message = "文件资源不能为空") MultipartFile[] file) throws IOException {
        int count = bbsMusicService.release(bbsMusic, userId, file);
        if(count > 0){
            return ResponseResultUtil.success(count);
        }else {
            return ResponseResultUtil.failed("发布失败");
        }
    }

    @ApiOperation(value = "获取我的热门音乐")
    @GetMapping("/getHotMusic")
    public ResponseResultUtil getHotMusic(@RequestParam(value = "userId") Long userId){
        List<BbsMusic> hotMusicList = bbsMusicService.getHotMusic(userId);
        return ResponseResultUtil.success(hotMusicList);
    }

}
