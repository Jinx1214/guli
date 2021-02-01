package com.atguigu.ucenter.Utils;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

@Component
public class ConstantUtil implements InitializingBean {


    @Value("${wx.open.app_id}")
    private   String appId;
    @Value("${wx.open.app_secret}")
    private   String appSecret;
    @Value("${wx.open.redirect_url}")
    private   String redirectUrl;

    public static String APPID;
    public static String AppSecret;
    public static String RedirectUrl;


    @Override
    public void afterPropertiesSet() throws Exception {
        APPID = this.appId;
        AppSecret = this.appSecret;
        RedirectUrl = this.redirectUrl;
    }
}
