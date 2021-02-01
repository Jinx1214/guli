package com.atguigu.service.service;

import com.atguigu.service.entity.EduCourseDescription;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程简介 服务类
 * </p>
 *
 * @author testjava
 * @since 2020-12-31
 */
public interface EduCourseDescriptionService extends IService<EduCourseDescription> {

    void removeByCourseId(String id);
}
