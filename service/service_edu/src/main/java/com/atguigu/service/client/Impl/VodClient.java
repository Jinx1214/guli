package com.atguigu.service.client.Impl;

import com.atguigu.Result.R;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class VodClient implements com.atguigu.service.client.VodClient {
    @Override
    public R deleteFile(String id) {
        return R.error().message("删除视频错误");
    }

    @Override
    public R delete(List<String> videoIdList) {
        return R.error().message("删除多个视频错误");
    }
}
