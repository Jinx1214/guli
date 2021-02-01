package com.atguigu.order.cliet;

import com.atguigu.Result.webVo.CoursePublishVoOrder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("service-edu")
public interface courseClient {

    //通过课程id获取课程详情接口
    @GetMapping("/eduservice/coursefron/courseInfo/{id}")
    public CoursePublishVoOrder getCourseInfoOrder(@PathVariable("id") String id);
}
