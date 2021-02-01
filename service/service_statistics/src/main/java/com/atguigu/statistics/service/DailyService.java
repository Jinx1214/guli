package com.atguigu.statistics.service;

import com.atguigu.statistics.entity.Daily;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务类
 * </p>
 *
 * @author testjava
 * @since 2021-01-19
 */
public interface DailyService extends IService<Daily> {

    void updateStaInfo(String day);
    //返回echarts数据模型接口
    Map getDiaryInfo(String type, String start, String end);
}
