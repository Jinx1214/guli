package com.atguigu.Vod.Service.impl;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.atguigu.Vod.Service.VodService;
import com.atguigu.Vod.Utils.ConstantUtils;
import com.atguigu.Vod.Utils.InitAliyun;
import org.apache.poi.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Service
public class VodServiceImpl implements VodService {


    @Override
    public String uploadVod(MultipartFile file) {


        String fileName = file.getOriginalFilename();
        String title = fileName.substring(0,fileName.lastIndexOf("."));

        InputStream inputStream = null;
        try {
            inputStream = file.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String VodId = "";
        UploadStreamRequest request = new UploadStreamRequest(ConstantUtils.ID,ConstantUtils.Key, title, fileName, inputStream);
        UploadVideoImpl uploader = new UploadVideoImpl();
        UploadStreamResponse response = uploader.uploadStream(request);
        System.out.print("RequestId=" + response.getRequestId() + "\n");  //请求视频点播服务的请求ID
        if (response.isSuccess()) {
            System.out.print("VideoId=" + response.getVideoId() + "\n");
            VodId = response.getVideoId();
        } else { //如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因
            System.out.print("VideoId=" + response.getVideoId() + "\n");
            System.out.print("ErrorCode=" + response.getCode() + "\n");
            System.out.print("ErrorMessage=" + response.getMessage() + "\n");
        }
        return VodId;
    }

    @Override
    public void delete(String id) {
        //删除视频服务
        //初始化客户端
        DefaultAcsClient defaultAcsClient = InitAliyun.initVodClient(ConstantUtils.ID, ConstantUtils.Key);

        //创建删除request
        DeleteVideoRequest request = new DeleteVideoRequest();
        request.setVideoIds(id);

        //执行操作
        try {
            defaultAcsClient.getAcsResponse(request);
        } catch (ClientException e) {
            e.printStackTrace();
        }
    }

    //删除多个视频
    @Override
    public void deleteIds(List id) {
        //将list集合进行转化  这样会将 list集合遍历输出 成  元素,元素,元素 的字符串 符合request删除多个视频id的格式
        String ids = StringUtil.join(id.toArray(), ",");

        DefaultAcsClient defaultAcsClient = InitAliyun.initVodClient(ConstantUtils.ID, ConstantUtils.Key);
        //执行操作
        try {
            //创建删除request
            DeleteVideoRequest request = new DeleteVideoRequest();
            //执行操作
            request.setVideoIds(ids);

            defaultAcsClient.getAcsResponse(request);
        } catch (ClientException e) {
            e.printStackTrace();
        }

    }

    @Override
    public String getPlayAuth(String id) throws ClientException {
        //初始化客户端
        DefaultAcsClient defaultAcsClient = InitAliyun.initVodClient(ConstantUtils.ID, ConstantUtils.Key);
        //获取得到播放凭证的对象
        GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
        request.setVideoId(id);

        GetVideoPlayAuthResponse acsResponse = defaultAcsClient.getAcsResponse(request);

        String playAuth = acsResponse.getPlayAuth();
        return playAuth;
    }
}
