package com.cjj.qlemusic.admin.controller;

import com.cjj.qlemusic.admin.dto.AmsAuthor;
import com.cjj.qlemusic.admin.service.AmsAuthorService;
import com.cjj.qlemusic.common.util.PageUtil;
import com.cjj.qlemusic.common.util.ResponseResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 后台管理歌手Controller
 */
@Api(tags = "AmsAuthorController" , description = "管理歌手信息")
@RestController
@RequestMapping("/author")
public class AmsAuthorController {
    @Autowired
    private AmsAuthorService amsAuthorService;


    @ApiOperation(value = "获取所有歌手信息")
    @RequestMapping(value = "/getAuthorList",method = RequestMethod.GET)
    public ResponseResultUtil getAuthorList(@RequestParam(value = "pageNum",defaultValue = "1") Integer pageNum,
                                            @RequestParam(value = "pageSize",defaultValue = "5") Integer pageSize){
        List<AmsAuthor> authorList = amsAuthorService.getAll(pageNum, pageSize);
        return ResponseResultUtil.success(PageUtil.restPage(authorList));
    }
}
