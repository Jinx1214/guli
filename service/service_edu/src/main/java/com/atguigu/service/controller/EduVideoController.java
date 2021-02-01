package com.atguigu.service.controller;


import com.atguigu.Result.R;
import com.atguigu.service.client.VodClient;
import com.atguigu.service.entity.EduVideo;
import com.atguigu.service.service.EduVideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-12-31
 */
@RestController
@RequestMapping("/service/video")
@CrossOrigin
public class EduVideoController {
    @Autowired
    private EduVideoService videoService;

    @Autowired
    private VodClient client;
    //添加小节
    @PostMapping("addVideo")
    public R addVideo(@RequestBody(required = false) EduVideo eduVideo){
        System.out.println(eduVideo);
      videoService.save(eduVideo);
     return R.ok();
    }


    //删除小节
    @DeleteMapping("deleteVideo/{id}")
    public R deleteVideo(@PathVariable String id){
        //删除小节的同时去删除视频信息
        //根据小节id查询视频id
        EduVideo video = videoService.getById(id);
        String vid = video.getVideoSourceId();
        //根据视频id删除视频在删除小节 判断是否有视频id
        if(!StringUtils.isEmpty(vid)){
            client.deleteFile(vid);
        }




        videoService.removeById(id);
        return R.ok();
    }

    //编辑小节
    @PostMapping("editVideo")
    public R editVideo(@RequestBody EduVideo eduVideo){
        EduVideo byId = videoService.getById(eduVideo.getId());
        videoService.updateById(eduVideo);
        return R.ok().data("video",byId);
    }
    //获取小节信息
    @GetMapping("getVideo/{id}")
    public R getVideo(@PathVariable("id")String id){
        EduVideo e = videoService.getById(id);
        return R.ok().data("video",e);
    }

}

