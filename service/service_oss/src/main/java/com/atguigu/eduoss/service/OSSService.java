package com.atguigu.eduoss.service;

import org.springframework.web.multipart.MultipartFile;

public interface OSSService {
    String uploadFile(MultipartFile multipartFile);
}
