package com.atguigu.msm.service.Impl;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.atguigu.msm.service.MsmService;
import com.atguigu.msm.utils.RandomUtil;
import org.apache.catalina.users.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.rmi.ServerException;
import java.util.HashMap;
import java.util.Map;

@Service
public class MsmServiceImpl implements MsmService {


    @Autowired
    private RedisTemplate<String,String> redisTemplate;
    @Override
    public boolean sendCode(String phone) {
        //生成验证码
        String fourBitRandom = RandomUtil.getFourBitRandom();
        Map<String,String> code = new HashMap<>();
        code.put("code",fourBitRandom);

        //验证手机号
        if(StringUtils.isEmpty(phone)) return false;
        DefaultProfile profile =
                DefaultProfile.getProfile("default", "LTAI4GBMLqXNwtnWLVW9T3XZ", "mVvRcSGqKmwemGLV7phuh56nmZd8l5");


        IAcsClient client = new DefaultAcsClient(profile);
        CommonRequest request = new CommonRequest();
//request.setProtocol(ProtocolType.HTTPS);
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");


        request.putQueryParameter("PhoneNumbers", phone);
        request.putQueryParameter("SignName", "九职410个人专用");
        request.putQueryParameter("TemplateCode", "SMS_205127988");
        request.putQueryParameter("TemplateParam", JSONObject.toJSONString(code));
        try {
            //将验证码放入redis中
            redisTemplate.opsForValue().set(phone,fourBitRandom);
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());
            return response.getHttpResponse().isSuccess();
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return false;
    }
}


