package com.atguigu.service.service.impl;

import com.atguigu.service.entity.EduCourseDescription;
import com.atguigu.service.entity.EduVideo;
import com.atguigu.service.mapper.EduCourseDescriptionMapper;
import com.atguigu.service.service.EduCourseDescriptionService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 课程简介 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-12-31
 */
@Service
public class EduCourseDescriptionServiceImpl extends ServiceImpl<EduCourseDescriptionMapper, EduCourseDescription> implements EduCourseDescriptionService {

    @Override
    public void removeByCourseId(String id) {
        QueryWrapper<EduCourseDescription> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id",id);
        baseMapper.delete(queryWrapper);
    }
}
