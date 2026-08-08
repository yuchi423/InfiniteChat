package com.yuchi.userservice.utils;

import cn.hutool.core.util.StrUtil;
import io.minio.GetPresignedObjectUrlArgs;
import io.minio.MinioClient;
import io.minio.http.Method;
import jakarta.annotation.Resource;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class OssUtils {

    @Resource
    private MinioClient minioClient;

    @Value("${minio.url}")
    private String url;

    @SneakyThrows
    public String uploadUrl(String bucketName, String objectName, Integer expires){
        //生成预签名URL，允许客户端在限定时间内通过改URL执行一次被允许的操作
        return minioClient.getPresignedObjectUrl(
                GetPresignedObjectUrlArgs.builder()
                        .method(Method.PUT)
                        .bucket(bucketName)
                        .object(objectName)
                        .expiry(expires, TimeUnit.SECONDS)
                        .build());
    }

    public String downUrl(String bucketName, String fileName) {
        return url + StrUtil.SLASH + bucketName + StrUtil.SLASH + fileName;
    }

}
