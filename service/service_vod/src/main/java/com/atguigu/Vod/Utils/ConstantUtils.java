package com.atguigu.Vod.Utils;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ConstantUtils implements InitializingBean {
    @Value("${aliyun.vod.file.keyid}")
    private  String ACCESS_ID;
    @Value("${aliyun.vod.file.keysecret}")
    private  String ACCESS_KEY;

    public static String ID;

    public static String Key;
    @Override
    public void afterPropertiesSet() throws Exception {
        this.ID = this.ACCESS_ID;
        this.Key = this.ACCESS_KEY;
    }
}
