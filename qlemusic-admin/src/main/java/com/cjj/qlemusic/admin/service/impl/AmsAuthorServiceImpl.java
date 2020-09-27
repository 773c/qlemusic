package com.cjj.qlemusic.admin.service.impl;

import com.cjj.qlemusic.admin.dao.AmsAuthorDao;
import com.cjj.qlemusic.admin.dto.AmsAuthor;
import com.cjj.qlemusic.admin.service.AmsAuthorService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 后台管理歌手Service实现类
 */
@Service
public class AmsAuthorServiceImpl implements AmsAuthorService {
    @Autowired
    private AmsAuthorDao amsAuthorDao;

    @Override
    public List<AmsAuthor> getAll(Integer pageNum,Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        return amsAuthorDao.selectAll();
    }
}
