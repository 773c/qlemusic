package com.cjj.qlemusic.admin.service;

import com.cjj.qlemusic.admin.dto.OssCallback;
import com.cjj.qlemusic.admin.dto.OssPolicy;

import javax.servlet.http.HttpServletRequest;

/**
 * OSS上传管理Service
 */
public interface OssService {
    /**
     * 生成OSS上传策略
     */
    OssPolicy policy();

    /**
     * 上传成功回调
     */

    OssCallback callback(HttpServletRequest request);
}
