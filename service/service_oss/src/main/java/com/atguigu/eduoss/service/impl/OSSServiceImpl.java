package com.atguigu.eduoss.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.atguigu.eduoss.service.OSSService;
import com.atguigu.eduoss.utils.ContantConfig;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Service
public class OSSServiceImpl implements OSSService {
    @Override
    public String uploadFile(MultipartFile multipartFile) {
        // Endpoint以杭州为例，其它Region请按实际情况填写。
        String endpoint = ContantConfig.ENDPOINT;
        // 云账号AccessKey有所有API访问权限，建议遵循阿里云安全最佳实践，创建并使用RAM子账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建。
        String accessKeyId = ContantConfig.ACCESSKEYID;
        String accessKeySecret = ContantConfig.ACEESEEKEYSECRET;
        String bucketName= ContantConfig.BUCKETNAME;
        //将文件添加uuid保证唯一性
        String fileName = UUID.randomUUID().toString() + multipartFile.getOriginalFilename() ;
        //可以使用工具类将文件进行分类
        DateTime dateTime = new DateTime();

        fileName =  dateTime.toString("yyyy/MM/dd")+ fileName;

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        // 上传文件流。
        InputStream inputStream = null;
        try {
            inputStream = multipartFile.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ossClient.putObject(bucketName, fileName, inputStream);

        // 关闭OSSClient。
        ossClient.shutdown();
        return "https://"+bucketName+"."+endpoint+"/"+fileName;

    }
}
