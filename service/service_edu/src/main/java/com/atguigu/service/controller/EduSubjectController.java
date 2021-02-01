package com.atguigu.service.controller;


import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.atguigu.Result.R;
import com.atguigu.service.entity.vo.OneSubjectName;
import com.atguigu.service.service.EduSubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-12-30
 */
@RestController
@RequestMapping("/service/subject")
@CrossOrigin
public class EduSubjectController {

    @Autowired
    private EduSubjectService service;

    @PostMapping("/addSubject")
    public R uploadExcel(MultipartFile file){
        //调用service进行文件的读取和存入数据库
        service.addSubject(file);
        return R.ok();
    }
    @GetMapping("/getAllSubjectList")
    public R getAllList(){
        List<OneSubjectName>  list = service.getAllSubjectList();
        return R.ok().data("AllList",list);
    }

}

