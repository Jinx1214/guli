package com.atguigu.statistics.controller;


import com.atguigu.Result.R;
import com.atguigu.Result.RandomUtil;
import com.atguigu.statistics.client.memberClient;
import com.atguigu.statistics.entity.Daily;
import com.atguigu.statistics.service.DailyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.annotation.Schedules;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Random;

/**
 * <p>
 * 网站统计日数据 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-01-19
 */
@RestController
@RequestMapping("/statistics/daily")
@CrossOrigin
public class DailyController {
    @Autowired
    private com.atguigu.statistics.client.memberClient memberClient;
    @Autowired
    private DailyService dailyService;

    @GetMapping("getStaInfo/{day}")
    public R getStaInfo(@PathVariable String day){
    dailyService.updateStaInfo(day);
    return R.ok();
    }

    //echarts数据查询接口
    //因为要返回数组类型，所以后端向前端返回集合数据模型
    @GetMapping("getDiaryInfo/{type}/{start}/{end}")
    public R getDiaryInfo(@PathVariable String type,
                          @PathVariable String start,
                          @PathVariable String end){
        //返回echarts数据模型接口
        Map map =  dailyService.getDiaryInfo(type,start,end);
        return R.ok().data(map);
    }


}

