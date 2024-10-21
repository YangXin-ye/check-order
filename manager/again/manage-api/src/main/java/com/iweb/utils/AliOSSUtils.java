package com.iweb.utils;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

/**
 * @Author 
 * @Create 2024-06-25-9:51
 * @Message
 **/

@Component
public class AliOSSUtils {





    /**
     * 实现上传图片到OSS
     */
    public String upload(MultipartFile file) throws IOException {
//        AliOss aliOss = new AliOss();
//        String endpoint = aliOss.getEndpoint();
//        String accessKeyId = aliOss.getAccessKeyId();
//        String accessKeySecret = aliOss.getAccessKeySecret();
//        String bucketName = aliOss.getBucketName();
//        System.out.println(aliOss);
//        // 获取上传的文件的输入流
//        InputStream inputStream = file.getInputStream();
//
//        // 避免文件覆盖
//        String originalFilename = file.getOriginalFilename();
//        String fileName = UUID.randomUUID().toString() + originalFilename.substring(originalFilename.lastIndexOf("."));
//
//        //上传文件到 OSS
//        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
//        ossClient.putObject(bucketName, fileName, inputStream);
//
//        //文件访问路径
//        String url = endpoint.split("//")[0] + "//" + bucketName + "." + endpoint.split("//")[1] + "/" + fileName;
//        // 关闭ossClient
//        ossClient.shutdown();
        return "";// 把上传到oss的路径返回
    }
}
