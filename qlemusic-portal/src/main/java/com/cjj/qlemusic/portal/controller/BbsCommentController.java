package com.cjj.qlemusic.portal.controller;


import com.cjj.qlemusic.common.util.ResponseResultUtil;
import com.cjj.qlemusic.portal.entity.BbsReplyuserComment;
import com.cjj.qlemusic.portal.entity.BbsUserComment;
import com.cjj.qlemusic.portal.service.BbsCommentService;
import com.cjj.qlemusic.security.entity.UmsUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Api(value = "评论管理")
@RestController
@RequestMapping("/comment")
public class BbsCommentController {
    @Autowired
    private BbsCommentService bbsCommentService;

    @ApiOperation(value = "评论")
    @PostMapping("/user")
    public ResponseResultUtil userComment(@RequestBody @Validated BbsUserComment bbsUserComment, BindingResult result) {
        int count = 0;
        try {
            count = bbsCommentService.userComment(bbsUserComment);
            if(count>0){
                return ResponseResultUtil.success(count);
            }
            else {
                return ResponseResultUtil.failed("评论失败");
            }
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseResultUtil.failed("未知错误");
        }
    }

    @ApiOperation(value = "回复评论")
    @PostMapping("/replyuser")
    public ResponseResultUtil replyuserComment(@RequestBody @Validated BbsReplyuserComment bbsReplyuserComment, BindingResult result) {
        int count = 0;
        try {
            count = bbsCommentService.replyuserComment(bbsReplyuserComment);
            if(count>0){
                return ResponseResultUtil.success(count);
            }
            else {
                return ResponseResultUtil.failed("回复失败");
            }
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseResultUtil.failed("未知错误");
        }
    }

    @ApiOperation(value = "获取对应音乐的评论")
    @GetMapping("/getCommentByMusic")
    public ResponseResultUtil getCommentByMusic(@RequestParam(value = "musicId") Long musicId) {
        List<BbsUserComment> commentByMusic = null;
        try {
            commentByMusic = bbsCommentService.getCommentByMusicId(musicId);
            if (commentByMusic != null) {
                return ResponseResultUtil.success(commentByMusic);
            } else {
                return ResponseResultUtil.failed("未知错误");
            }
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseResultUtil.failed("未知错误");
        }
    }

    @ApiOperation(value = "获取评论的用户")
    @PostMapping("/getUserByComment")
    public ResponseResultUtil getUserByComment(@RequestParam(value = "userIds") List<Long> userIds) {
        try {
            List<UmsUser> umsUserList = bbsCommentService.getUserByComment(userIds);
            return ResponseResultUtil.success(umsUserList);
        } catch (Exception e) {
            return ResponseResultUtil.failed("未知错误");
        }
    }

}
