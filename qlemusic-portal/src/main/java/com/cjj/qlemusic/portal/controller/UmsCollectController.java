package com.cjj.qlemusic.portal.controller;

import com.cjj.qlemusic.common.util.ResponseResultUtil;
import com.cjj.qlemusic.security.service.UmsCollectService;
import com.cjj.qlemusic.security.entity.UmsCollect;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Api(value = "收藏管理")
@RestController
@RequestMapping("/collect")
@Validated
public class UmsCollectController {
    @Autowired
    private UmsCollectService collectService;

    @ApiOperation(value = "创建收藏夹")
    @PostMapping(value = "/createCollect")
    public ResponseResultUtil createCollect(@RequestParam(value = "userId")Long userId,
                                            @RequestBody @Validated UmsCollect umsCollect,
                                            BindingResult result) {
        Long collectId = collectService.createCollect(userId,umsCollect);
        if(collectId != null)
            return ResponseResultUtil.success(collectId);
        else
            return ResponseResultUtil.failed("创建收藏夹失败");
    }

    @ApiOperation(value = "添加到收藏夹")
    @GetMapping(value = "/addCollect")
    public ResponseResultUtil addCollect(@RequestParam(value = "collectId")Long collectId,
                                         @RequestParam(value = "musicId")Long musicId) {
        int count = collectService.addCollect(collectId,musicId);
        if(count > 0)
            return ResponseResultUtil.success(count);
        else
            return ResponseResultUtil.failed("添加收藏夹失败");
    }

    @ApiOperation(value = "获取所有收藏夹")
    @GetMapping(value = "/getCollectList")
    public ResponseResultUtil getCollectList(@RequestParam(value = "id")Long id) {
        List<UmsCollect> collectList = collectService.getCollectList(id);
        return ResponseResultUtil.success(collectList);
    }

    @ApiOperation(value = "从相应收藏夹中删除内容")
    @PostMapping(value = "/deleteCollectContent/{collectMusicId}")
    public ResponseResultUtil deleteCollectContent(@PathVariable Long collectMusicId) {
        System.out.println("collectMusicId:"+collectMusicId);
        int count = collectService.deleteCollectContent(collectMusicId);
        if(count > 0)
            return ResponseResultUtil.success(count);
        else
            return ResponseResultUtil.failed("删除收藏夹内容失败");
    }

    @ApiOperation(value = "删除空内容的收藏夹，暂时只删除收藏表，不动关联表")
    @GetMapping(value = "/deleteCollect")
    public ResponseResultUtil deleteCollect(
            @RequestParam(value = "id")
            @Min(value = 1,message = "默认收藏夹无法删除")
            @NotNull(message = "收藏夹id不能为空") Long id) {
        System.out.println(id);
        int count = collectService.deleteCollect(id);
        if(count > 0)
            return ResponseResultUtil.success(count);
        else
            return ResponseResultUtil.failed("删除空内容收藏夹失败");
    }

    @ApiOperation(value = "删除收藏夹并移动内容，暂时只删除收藏表，不动关联表")
    @GetMapping(value = "/deleteCollectAndMove")
    public ResponseResultUtil deleteCollectAndMove(
            @RequestParam(value = "id")
            @Min(value = 1,message = "默认收藏夹无法删除")
            @NotNull(message = "收藏夹id不能为空") Long id,
            @RequestParam(value = "moveId") Long moveId) {
        System.out.println(id+"..."+moveId);
        int count = collectService.deleteCollectAndMove(id,moveId);
        if(count > 0)
            return ResponseResultUtil.success(count);
        else
            return ResponseResultUtil.failed("删除收藏夹并移动内容失败");
    }

    @ApiOperation(value = "删除收藏夹和内容，删除收藏表和关联表")
    @GetMapping(value = "/deleteCollectAndContent")
    public ResponseResultUtil deleteCollectAndContent(
            @RequestParam(value = "id")
            @Min(value = 1,message = "默认收藏夹无法删除")
            @NotNull(message = "收藏夹id不能为空") Long id) {
        int count = collectService.deleteCollectAndContent(id);
        if(count > 0)
            return ResponseResultUtil.success(count);
        else
            return ResponseResultUtil.failed("删除收藏夹和内容失败");
    }

    @ApiOperation(value = "批量移动收藏夹内容")
    @PostMapping(value = "/batchMoveContent")
    public ResponseResultUtil batchMoveContent(
            @RequestParam(value = "collectMusicIds")
            @NotEmpty(message = "请选择需要移动的内容") List<Long> collectMusicIds,
            @RequestParam(value = "id")
            @NotNull(message = "请选择收藏夹") Long id) {
        int count = collectService.batchMoveContent(collectMusicIds,id);
        if(count > 0)
            return ResponseResultUtil.success(count);
        else
            return ResponseResultUtil.failed("删除收藏夹失败");
    }
}
