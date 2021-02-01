package com.atguigu.Vod.Service;

import com.aliyuncs.exceptions.ClientException;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface VodService {
    String uploadVod(MultipartFile file);

    void delete(String id);

    void deleteIds(List videoIdList);

    String getPlayAuth(String id) throws ClientException;
}
