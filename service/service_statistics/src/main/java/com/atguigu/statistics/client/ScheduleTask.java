package com.atguigu.statistics.client;

import com.atguigu.statistics.service.DailyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Component
//定时任务注解
@EnableScheduling
public class ScheduleTask {

    @Autowired
    private DailyService dailyService;
        //添加定时任务 每天凌晨一点将前一天的数据查询出来
        @Scheduled(cron = "0 0 1 * * ? ")
    public void Task(){
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Calendar c = Calendar.getInstance();
            c.setTime(new Date());
            Date end = c.getTime();
            String dqrq= format.format(end);//当前日期

            c.add(Calendar.DATE, -1);
            Date start = c.getTime();
            String qyt= format.format(start);//前一天

           dailyService.updateStaInfo(qyt);

    }

}
