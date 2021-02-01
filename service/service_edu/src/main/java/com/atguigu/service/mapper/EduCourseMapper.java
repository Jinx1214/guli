package com.atguigu.service.mapper;

import com.atguigu.service.entity.EduCourse;
import com.atguigu.service.entity.vo.CoursePublishVo;
import com.atguigu.service.entity.vo.CourseWebVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author testjava
 * @since 2020-12-31
 */
public interface EduCourseMapper extends BaseMapper<EduCourse> {

    //获取课程信息
    CoursePublishVo getCoursePublish(String id);

    CourseWebVo getCourseInfo(String id);
}
