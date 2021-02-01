package com.atguigu.statistics.service.impl;

import com.atguigu.Result.R;
import com.atguigu.statistics.client.memberClient;
import com.atguigu.statistics.entity.Daily;
import com.atguigu.statistics.mapper.DailyMapper;
import com.atguigu.statistics.service.DailyService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-01-19
 */
@Service
public class DailyServiceImpl extends ServiceImpl<DailyMapper, Daily> implements DailyService {
    @Autowired
    private memberClient client;

    @Override
    public void updateStaInfo(String day) {
        //判断表中是否有当天数据，如果有则将数据删除重新添加
        QueryWrapper<Daily> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("date_calculated",day);
        Integer integer = baseMapper.selectCount(queryWrapper);
        if(integer>0){
            baseMapper.delete(queryWrapper);
        }
        //查询当天注册人数的数据
        R staInfo = client.getStaInfo(day);
        Integer count = (Integer) staInfo.getData().get("count");
        // 将查询出来的数据封装进我们的统计表内
        Daily daily = new Daily();
        daily.setRegisterNum(count);
        daily.setCourseNum((int) (Math.random()*100));
        daily.setVideoViewNum(10);
        daily.setDateCalculated(day);
        daily.setLoginNum(1);
        baseMapper.insert(daily);
    }

    //返回echarts数据模型接口
    @Override
    public Map getDiaryInfo(String type, String start, String end) {
        //根据选择日期 和 查询条件进行查询
        QueryWrapper<Daily> queryWrapper = new QueryWrapper<>();
        queryWrapper.select(type,"date_calculated");
        queryWrapper.between("date_calculated",start,end);


        List<Daily> dailies = baseMapper.selectList(queryWrapper);
        //将数据进行遍历封装
        List<String> diaryList = new ArrayList<>();
        List<Integer> numberList = new ArrayList<>();

        for (int i = 0; i < dailies.size(); i++) {
            Daily daily = dailies.get(i);
            diaryList.add(daily.getDateCalculated());
            switch (type){
                case "login_num":
                    numberList.add(daily.getLoginNum());
                    break;
                case "register_num":
                    numberList.add(daily.getRegisterNum());
                    break;
                case "video_view_num":
                    numberList.add(daily.getVideoViewNum());
                    break;
                case "course_num":
                    numberList.add(daily.getCourseNum());
                    break;
                default:
                    break;

            }
        }
        Map<String,Object> map = new HashMap();

        map.put("diraylist",diaryList);
        map.put("numberList",numberList);
        System.out.println(map);

        return map;
    }
}
