package com.atguigu.service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("service-order")
public interface OrderClient {


    @GetMapping("/order/order/getCoursePayStatus/{courseId}/{memeberId}")
    public boolean geCourseInfo(@PathVariable("courseId") String courseId,
                                @PathVariable("memeberId") String memeberId);
}
