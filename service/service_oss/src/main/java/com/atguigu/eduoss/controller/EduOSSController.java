package com.atguigu.eduoss.controller;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.atguigu.Result.R;
import com.atguigu.eduoss.service.OSSService;
import com.atguigu.eduoss.utils.ContantConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@RestController
@RequestMapping("/eduoss/upload")
@CrossOrigin
public class EduOSSController {

    @Autowired
    private OSSService ossService;
    @PostMapping
    public R UploadAvatar(@RequestParam("file") MultipartFile multipartFile) throws IOException {
       String url =  ossService.uploadFile(multipartFile);
       return R.ok().data("url",url);

    }
}
