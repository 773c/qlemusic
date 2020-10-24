package com.cjj.qlemusic.security.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONUtil;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.common.utils.BinaryUtil;
import com.aliyun.oss.model.MatchMode;
import com.aliyun.oss.model.PolicyConditions;
import com.aliyun.oss.model.PutObjectRequest;
import com.cjj.qlemusic.common.util.FileUploadUtil;
import com.cjj.qlemusic.security.entity.OssCallback;
import com.cjj.qlemusic.security.entity.OssCallbackParam;
import com.cjj.qlemusic.security.entity.OssPolicy;
import com.cjj.qlemusic.security.service.OssService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.util.Date;


@Service
public class OssServiceImpl implements OssService {
    private static final Logger LOGGER = LoggerFactory.getLogger(OssServiceImpl.class);

    @Value("${aliyun.oss.endpoint}")
    private String endpoint;
    @Value("${aliyun.oss.bucketName}")
    private String bucketName;
    @Value("${aliyun.oss.policy.expire}")
    private long expire;
    @Value("${aliyun.oss.maxSize}")
    private long maxSize;
    @Value("${aliyun.oss.callback}")
    private String callback;
    @Value("${aliyun.oss.dir.prefix}")
    private String dirPrefix;
    @Value("${aliyun.oss.dir.audiosPrefix}")
    private String audiosPrefix;

    @Autowired
    private OSSClient ossClient;

    @Override
    public OssPolicy policy() {
        OssPolicy ossPolicy = new OssPolicy();
        //存储目录
        String dir = dirPrefix+ DateUtil.format(new Date(),"yyyyMMdd");
        //签名有效期
        long expirationTime = System.currentTimeMillis() + expire * 1000;
        Date expirationDate = new Date(expirationTime);
        //文件大小（将M转为B）
        long fileSize = maxSize * 1024 * 1024;
        //回调设置
        OssCallbackParam ossCallbackParam = new OssCallbackParam();
        ossCallbackParam.setCallbackUrl(callback);
        ossCallbackParam.setCallbackBody("filename=${object}&size=${size}&mimeType=${mimeType}&height=${imageInfo.height}&width=${imageInfo.width}");
        ossCallbackParam.setCallbackBodyType("application/x-www-form-urlencoded");
        //提交节点域名
        String host = "http://" + bucketName + "." + endpoint;
        try{
            PolicyConditions policyConditions = new PolicyConditions();
            //设置文件大小
            policyConditions.addConditionItem(PolicyConditions.COND_CONTENT_LENGTH_RANGE,0,fileSize);
            //设置文件存储路径前缀
            policyConditions.addConditionItem(MatchMode.StartWith,PolicyConditions.COND_KEY,dir);
            //获取postPolicy
            String postPolicy = ossClient.generatePostPolicy(expirationDate,policyConditions);
            //转换为二进制数据
            byte[] binaryData = postPolicy.getBytes("utf-8");
            //将二进制数据转换为加密字符串
            String policy = BinaryUtil.toBase64String(binaryData);
            //对policy生成签名
            String signature = ossClient.calculatePostSignature(postPolicy);
            //生成加密回调
            String callbackData = BinaryUtil.toBase64String(JSONUtil.toJsonStr(ossCallbackParam).getBytes("utf-8"));
            //返回结果
            ossPolicy.setAccessKeyId(ossClient.getCredentialsProvider().getCredentials().getAccessKeyId());
            ossPolicy.setPolicy(policy);
            ossPolicy.setSignature(signature);
            ossPolicy.setHost(host);
            ossPolicy.setDir(dir);
            ossPolicy.setCallback(callbackData);
        }catch (Exception e){
            LOGGER.error("签名生成失败",e);
        }
        return ossPolicy;
    }

    @Override
    public OssCallback callback(HttpServletRequest request) {
        System.out.println("callback执行了service");
        OssCallback ossCallback = new OssCallback();
        String filename = request.getParameter("filename");
        System.out.println("filename:"+filename);
        filename = "http://".concat(bucketName).concat(".").concat(endpoint).concat("/").concat(filename);
        System.out.println("filenamePath:"+filename);
        ossCallback.setFilename(filename);
        ossCallback.setSize(request.getParameter("size"));
        ossCallback.setMimeType(request.getParameter("mimeType"));
        ossCallback.setWidth(request.getParameter("width"));
        ossCallback.setHeight(request.getParameter("height"));
        return ossCallback;
    }

    @Override
    public String uploadOss(String fileName, InputStream inputStream) {
        String ossFilePath = getOssFileApiPath(fileName);
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName , ossFilePath , inputStream);
        ossClient.putObject(putObjectRequest);
        return ossFilePath;
    }

    @Override
    public String getOssFileApiPath(String filename) {
        if(FileUploadUtil.isAudioType(filename))
            return audiosPrefix + DateUtil.today() + "/" + filename;
        else
            return dirPrefix + DateUtil.today() + "/" + filename;
    }
}
