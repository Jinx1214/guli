package com.atguigu.statistics.client;

import com.atguigu.Result.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("service-ucenter")
public interface memberClient {

    @GetMapping("/ucenter/member/getStaInfo/{day}")
    public R getStaInfo(@PathVariable("day") String day);
}
