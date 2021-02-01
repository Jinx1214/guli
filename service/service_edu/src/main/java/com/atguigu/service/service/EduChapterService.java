package com.atguigu.service.service;

import com.atguigu.service.entity.EduChapter;
import com.atguigu.service.entity.vo.ChapterVo;
import com.atguigu.service.entity.vo.CourseInfo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author testjava
 * @since 2020-12-31
 */
public interface EduChapterService extends IService<EduChapter> {


    List<ChapterVo> getAllChapterList(String id);

    CourseInfo getInfoByCourseId(String id);

    void updateByCourseId(CourseInfo courseInfo);

    boolean deleteByVideoNumb(String id);

    void removeByCourseId(String id);
}
