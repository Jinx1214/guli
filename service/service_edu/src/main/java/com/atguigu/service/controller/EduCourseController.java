package com.atguigu.service.controller;


import com.atguigu.Result.R;
import com.atguigu.Result.webVo.CoursePublishVoOrder;
import com.atguigu.service.client.VodClient;
import com.atguigu.service.entity.EduCourse;
import com.atguigu.service.entity.vo.CourseInfo;
import com.atguigu.service.entity.vo.CoursePublishVo;
import com.atguigu.service.entity.vo.QueryCourseVo;
import com.atguigu.service.service.EduCourseService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-12-31
 */
@RestController
@RequestMapping("/service/course")
@CrossOrigin
public class EduCourseController {


    @Autowired
    private EduCourseService courseService;



    //查询所有课程
    @GetMapping("getAll")
    public R getAllCourse(){
        List<EduCourse> list = courseService.list(null);

        return R.ok().data("list",list);
    }
    //删除课程信息
    @DeleteMapping("delete/{id}")
    public R deleteCourse(@PathVariable("id")String id){
        //删除课程并同时删除视频资源
        courseService.removeByCourseId(id);
        return R.ok();
    }

    //添加课程
    @PostMapping("/addCourse")
    public R addCourse(@RequestBody CourseInfo courseInfo){
    String cid =  courseService.saveCourse(courseInfo);
    return R.ok().data("courseId",cid);
    }

    //获取课程信息
    @GetMapping("getCourse/{id}")
    public R getCourse(@PathVariable("id")String id){
        CoursePublishVo coursePublishVo =  courseService.getCourse(id);
        return R.ok().data("courseVo",coursePublishVo);
    }

    //更改课程发布的状态
        @PutMapping("publishCourse/{id}")
    public R publishCourse(@PathVariable("id")String id){
        EduCourse course = new EduCourse();
        course.setId(id);
        course.setStatus("Normal");
        courseService.updateById(course);
        return R.ok();
    }

    //课程的模糊查询带分页
    @PostMapping("queryCourse/{current}/{limit}")
    public R queryCourse(@PathVariable Integer current,
                         @PathVariable Integer limit,
                         @RequestBody QueryCourseVo queryCourseVo){
        System.out.println(queryCourseVo);

        QueryWrapper<EduCourse> queryWrapper = new QueryWrapper<>();

        Page<EduCourse> page = new Page<>(current,limit);
        String status = queryCourseVo.getStatus();
        String title = queryCourseVo.getTitle();
        if(status!=null){
            queryWrapper.eq("status",status);
        }

        if(title!=null){
            queryWrapper.eq("title",title);
        }

        courseService.page(page,queryWrapper);
        Map map =new HashMap();
        map.put("list",page.getRecords());
        map.put("total",page.getTotal());

        return R.ok().data(map);
    }

    @GetMapping("getCourseVo/{id}")
    public CoursePublishVoOrder getCourseVo(@PathVariable("id")String id){
        CoursePublishVo coursePublishVo =  courseService.getCourse(id);
        CoursePublishVoOrder publishVoOrder = new CoursePublishVoOrder();
        BeanUtils.copyProperties(coursePublishVo,publishVoOrder);

        return publishVoOrder;
    }




}

