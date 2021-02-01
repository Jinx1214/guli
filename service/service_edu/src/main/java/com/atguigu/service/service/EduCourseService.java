package com.atguigu.service.service;

import com.atguigu.service.entity.EduCourse;
import com.atguigu.service.entity.vo.*;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author testjava
 * @since 2020-12-31
 */
public interface EduCourseService extends IService<EduCourse> {

    String saveCourse(CourseInfo courseInfo);

    CoursePublishVo getCourse(String id);

    void removeByCourseId(String id);


    Map getCourseList(Page<EduCourse> page, CourseSearchVO courseSearchVO);

    CourseWebVo getCourseInfo(String id);
}
