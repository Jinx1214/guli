package com.atguigu.order.cliet;

import com.atguigu.Result.webVo.Memberorder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;

@FeignClient("service-ucenter")
public interface memberClient {

    //根据会员id获取会员信息
    @GetMapping("/ucenter/member/userInfo/{id}")
    public Memberorder userInfovo(@PathVariable("id")String id);

}
