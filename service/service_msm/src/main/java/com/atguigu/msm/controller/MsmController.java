package com.atguigu.msm.controller;

import com.atguigu.Result.R;
import com.atguigu.msm.service.MsmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/msm")
@CrossOrigin
public class MsmController {
    @Autowired
    private MsmService msmService;


    @GetMapping ("/send/{phone}")
    public R sendMsm(@PathVariable String phone){
        //发送验证码
        boolean isSuccess= msmService.sendCode(phone);

        return isSuccess?R.ok(): R.error().data("status","发送验证码失败");

    }

}
