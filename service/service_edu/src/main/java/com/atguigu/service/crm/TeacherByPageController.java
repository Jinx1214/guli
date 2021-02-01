package com.atguigu.service.crm;

import com.atguigu.Result.R;
import com.atguigu.service.entity.EduCourse;
import com.atguigu.service.entity.EduTeacher;
import com.atguigu.service.service.EduCourseService;
import com.atguigu.service.service.EduTeacherService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/eduservice/getTeacherList")
@CrossOrigin
public class TeacherByPageController {

    @Autowired
    private EduTeacherService eduTeacherService;
    @Autowired
    private EduCourseService eduCourseService;

    //分页查询教师接口
    @GetMapping("/getTeacherList/{current}/{limit}")
    public R getList(@PathVariable long current,
                     @PathVariable long limit){
        Page<EduTeacher> page = new Page<>(current,limit);
        Map<String,Object> map = eduTeacherService.findByPage(page);
        return R.ok().data(map);
    }
    //查询老师详情
    @GetMapping("/geTeacherInfo/{id}")
    public R getInfo(@PathVariable String id){
        EduTeacher teacherInfo = eduTeacherService.getById(id);
        //查询老师演讲的课程
        QueryWrapper<EduCourse> eduCourseQueryWrapper = new QueryWrapper<>();
        eduCourseQueryWrapper.eq("teacher_id",teacherInfo.getId());
        List<EduCourse> courseList = eduCourseService.list(eduCourseQueryWrapper);
        System.out.println("1111");
        return R.ok().data("teacherInfo",teacherInfo).data("courseList",courseList);
    }

}
