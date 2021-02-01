package com.atguigu.service.service;

import com.atguigu.service.entity.EduTeacher;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author testjava
 * @since 2020-12-26
 */
public interface EduTeacherService extends IService<EduTeacher> {

    Map<String, Object> findByPage(Page<EduTeacher> page);
}
