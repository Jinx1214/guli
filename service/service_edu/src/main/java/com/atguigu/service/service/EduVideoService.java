package com.atguigu.service.service;

import com.atguigu.service.entity.EduVideo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author testjava
 * @since 2020-12-31
 */
public interface EduVideoService extends IService<EduVideo> {

    void removeByCourseId(String id);
}
