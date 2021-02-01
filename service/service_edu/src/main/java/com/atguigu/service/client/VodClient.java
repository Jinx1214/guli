package com.atguigu.service.client;

import com.atguigu.Result.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(value = "service-vod",fallback = com.atguigu.service.client.Impl.VodClient.class)
@Component
public interface VodClient {

    @DeleteMapping("/VodService/deleteFile/{id}")
    public R deleteFile(@PathVariable("id") String id);

    @DeleteMapping("/VodService/delete")
    public R delete(@RequestParam("videoIdList") List<String> videoIdList);
}
