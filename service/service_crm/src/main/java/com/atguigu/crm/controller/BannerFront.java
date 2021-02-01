package com.atguigu.crm.controller;


import com.atguigu.Result.R;
import com.atguigu.crm.entity.Banner;
import com.atguigu.crm.service.BannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


//前端用户获取数据接口
@RestController
@RequestMapping("/crm/bannerfront")
@CrossOrigin
public class    BannerFront {

    @Autowired
    private BannerService bannerService;

    //redis缓存注解
    @Cacheable(key = "'banner'",value = "AllList")
    @GetMapping("getList")
    public R getList(){
        List<Banner> banners = bannerService.getAll();
        return R.ok().data("AllList",banners);
    }
}
