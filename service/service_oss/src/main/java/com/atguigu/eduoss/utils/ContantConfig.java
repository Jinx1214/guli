package com.atguigu.eduoss.utils;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
//实现Spring提供的接口， 将私有属性对外暴露
@Component
public class ContantConfig implements InitializingBean {
    @Value("${bucketName}")
    private String bucketName;
    @Value("${endpoint}")
    private String endpoint;
    @Value("${accessKeyId}")
    private String accessKeyId;
    @Value("${accessKeySecret}")
    private String accessKeySecret;

    public static String BUCKETNAME;
    public static String ENDPOINT;
    public static String ACCESSKEYID;
    public static String ACEESEEKEYSECRET;
    @Override
    public void afterPropertiesSet() throws Exception {
        BUCKETNAME = this.bucketName;
        ENDPOINT = this.endpoint;
        ACCESSKEYID = this.accessKeyId;
        ACEESEEKEYSECRET = this.accessKeySecret;

    }
}
