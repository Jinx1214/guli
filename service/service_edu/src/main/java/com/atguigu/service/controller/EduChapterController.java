package com.atguigu.service.controller;


import com.atguigu.Result.R;
import com.atguigu.service.entity.EduChapter;
import com.atguigu.service.entity.vo.ChapterVo;
import com.atguigu.service.entity.vo.CourseInfo;
import com.atguigu.service.service.EduChapterService;
import com.atguigu.service.service.EduCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-12-31
 */
@RestController
@RequestMapping("/service/chapter")
@CrossOrigin
public class EduChapterController {

    @Autowired
    private EduChapterService chapterService;



    //获取课程ID获取课程大纲
    @GetMapping("getAllChapter/{id}")
    public R getAllChapterList(@PathVariable String id){
        List<ChapterVo> chapterVoList =  chapterService.getAllChapterList(id);
        return R.ok().data("AllList",chapterVoList);
    }

    //根据课程id获取课程信息
    @GetMapping("getCourse/{id}")
    public R getCourse(@PathVariable("id")String id){
        CourseInfo courseInfo = chapterService.getInfoByCourseId(id);
        return R.ok().data("courseInfo",courseInfo);
    }

    //修改课程信息
    @PostMapping("updateCourse")
    public R updateCourse(@RequestBody CourseInfo courseInfo){
        chapterService.updateByCourseId(courseInfo);
        return R.ok();
    }

    //添加章节信息
    @PostMapping("addChapter")
    public R addChapter(@RequestBody EduChapter chapter){
        chapterService.save(chapter);
        return R.ok();
    }

    //修改章节信息
    @PostMapping("updateChapter")
    public R updateChapter(@RequestBody EduChapter chapter){
        chapterService.updateById(chapter);
        return R.ok();
    }

    //删除章节信息
    @DeleteMapping("delete/{id}")
    public R deleteChapter(@PathVariable("id")String id){
        //如果章节下有小节则不让删除
        boolean flag =  chapterService.deleteByVideoNumb(id);
        return flag? R.ok(): R.error();

    }

    //通过章节id获取章节信息
    @GetMapping("getChapterById/{id}")
    public R getChapterById(@PathVariable("id")String id){
        EduChapter chapter = chapterService.getById(id);
        return R.ok().data("chapter",chapter);
    }

}

