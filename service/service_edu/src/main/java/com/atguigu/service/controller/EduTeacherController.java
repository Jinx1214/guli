package com.atguigu.service.controller;


import com.atguigu.Result.R;
import com.atguigu.service.entity.EduTeacher;
import com.atguigu.service.entity.TeacherQuery;
import com.atguigu.service.service.EduTeacherService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-12-26
 */
@RestController
@RequestMapping("/service/edu-teacher")
@CrossOrigin
public class EduTeacherController {

    @Autowired
    private EduTeacherService teacherService;
    //查找所有教师
    @GetMapping("findAll")
    public R findAll(){
        List<EduTeacher> list = teacherService.list(null);
        return R.ok().data("rows",list);
    }
    //删除教师
    @DeleteMapping({"delete/{id}"})
    public R deleted(@PathVariable String id){
        boolean b = teacherService.removeById(id);
        return b?R.ok():R.error();
    }

    //分页查询所有教师
    @GetMapping("teacherList/{current}/{limit}")
    public R teacherList(@PathVariable long current,
                         @PathVariable long limit){
        Page<EduTeacher> page = new Page<>(current,limit);
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();
        //排序
        wrapper.orderByDesc("gmt_create");

        teacherService.page(page, wrapper);

        return R.ok().data("total",page.getTotal()).data("rows",page.getRecords());
    }

    //条件查询加分页
    @PostMapping("getTeacher/{current}/{limit}")
    public R getTeacher(@PathVariable long current,
                        @PathVariable long limit,
                        @RequestBody(required = false)TeacherQuery teacherQuery){
        Page<EduTeacher> teacherPage = new Page<>(current,limit);

        //查询条件 这里要判断一下参数是否合格
        QueryWrapper<EduTeacher> queryWrapper = new QueryWrapper();
        String name = teacherQuery.getName();
        Integer level = teacherQuery.getLevel();
        String gmtCreat = teacherQuery.getGmtCreat();
        String gmtEnd = teacherQuery.getGmtEnd();
        if(!StringUtils.isEmpty(name)){
            queryWrapper.like("name",name);
        }

        if(!StringUtils.isEmpty(level)){
            queryWrapper.eq("level",level);
        }

            if(!StringUtils.isEmpty(gmtCreat)){
            queryWrapper.gt("gmt_create",gmtCreat);
        }

        if(!StringUtils.isEmpty(gmtEnd)){
            queryWrapper.le("gmt_modified",gmtEnd);
        }

        teacherService.page(teacherPage,queryWrapper);
        Map map = new HashMap();
        map.put("total",teacherPage.getTotal());
        map.put("rows",teacherPage.getRecords());

        return R.ok().data(map);
    }

    //添加讲师
    @PostMapping("addTeacher")
    public R addTeacher(@RequestBody EduTeacher eduTeacher){
        boolean save = teacherService.save(eduTeacher);
        return save? R.ok(): R.error();
    }

    //修改讲师  必须先查询数据在修改
    @GetMapping("getTeacher/{id}")
    public R getTeacher(@PathVariable long id){
        EduTeacher byId = teacherService.getById(id);
        return R.ok().data("teacher",byId);
    }

    @PostMapping("editTeacher")
    public R editTeacher(@RequestBody EduTeacher eduTeacher){
        boolean b = teacherService.updateById(eduTeacher);
        return b?R.ok(): R.error();

    }

}

