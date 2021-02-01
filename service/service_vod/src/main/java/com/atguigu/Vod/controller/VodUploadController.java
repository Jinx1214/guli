package com.atguigu.Vod.controller;

import com.aliyuncs.exceptions.ClientException;
import com.atguigu.Result.R;
import com.atguigu.Vod.Service.VodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/VodService")
@CrossOrigin
public class VodUploadController {

    @Autowired
    private VodService vodService;

    @PostMapping("upload")
    public R uploadVod(MultipartFile file) {
        String VodId =  vodService.uploadVod(file);
        return R.ok().data("VodId",VodId);

    }

    @DeleteMapping("deleteFile/{id}")
    public R deleteFile(@PathVariable String id){
        vodService.delete(id);
        return R.ok();
    }
    //删除多个视频接口
    @DeleteMapping("delete")
    public R delete(@RequestParam("videoIdList") List<String> videoIdList){
        vodService.deleteIds(videoIdList);
        return R.ok();
    }
    //通过id获取播放凭证
    @GetMapping("getPlayAuth/{id}")
    public R getPlayAuth(@PathVariable String id) throws ClientException {

        String playAuth = vodService.getPlayAuth(id);

        return R.ok().data("playAuth",playAuth);

    }
}
