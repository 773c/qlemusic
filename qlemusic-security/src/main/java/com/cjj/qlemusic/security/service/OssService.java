package com.cjj.qlemusic.security.service;





import com.cjj.qlemusic.security.entity.OssCallback;
import com.cjj.qlemusic.security.entity.OssPolicy;

import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;

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

    /**
     * 将文件上传到OSS
     * @param filename
     * @param inputStream
     */
    String uploadOss(String filename, InputStream inputStream);
    /**
     * 获取OSS上传路径
     * @param filename
     * @return
     */
    String getOssFileApiPath(String filename);
}
