package com.atguigu.service.service.impl;

import com.atguigu.service.entity.EduChapter;
import com.atguigu.service.entity.EduCourse;
import com.atguigu.service.entity.EduCourseDescription;
import com.atguigu.service.entity.EduVideo;
import com.atguigu.service.entity.vo.ChapterVo;
import com.atguigu.service.entity.vo.CourseInfo;
import com.atguigu.service.entity.vo.VideoVo;
import com.atguigu.service.mapper.EduChapterMapper;
import com.atguigu.service.service.EduChapterService;
import com.atguigu.service.service.EduCourseDescriptionService;
import com.atguigu.service.service.EduCourseService;
import com.atguigu.service.service.EduVideoService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-12-31
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {
    @Autowired
    private EduVideoService videoService;

    @Autowired
    private EduCourseService courseService;

    @Autowired
    private EduCourseDescriptionService eduCourseDescriptionService;

    @Autowired
    private EduChapterService chapterService;


    @Autowired
    private EduCourseService  eduCourseService;

    @Override
    //根据课程ID获取课程大纲
    public List<ChapterVo> getAllChapterList(String id) {
        //先获取一级课程信息
        QueryWrapper<EduChapter> eduChapterWrapper = new QueryWrapper<>();
        eduChapterWrapper.eq("course_id",id);

        List<EduChapter> eduChapters = baseMapper.selectList(eduChapterWrapper);


        //获取二级课程信息
        QueryWrapper<EduVideo> eduVideoWrapper = new QueryWrapper<>();
        eduVideoWrapper.eq("course_id",id);
        List<EduVideo> videoList = videoService.list(eduVideoWrapper);

        List<ChapterVo> finalList = new ArrayList<>();


        //封装一级课程
        for (int i = 0; i < eduChapters.size(); i++) {
            ChapterVo chapterVo = new ChapterVo();
            EduChapter eduChapter = eduChapters.get(i);
            BeanUtils.copyProperties(eduChapter,chapterVo);
            finalList.add(chapterVo);
            //封装二级课程
            List<VideoVo> videoVos = new ArrayList<>();
            for (int i1 = 0; i1 < videoList.size(); i1++) {
                EduVideo eduVideo1 = videoList.get(i1);
                //判断该小节是否是同一课程下
                if(eduChapter.getId().equals(eduVideo1.getChapterId())){
                    EduVideo eduVideo = videoList.get(i1);
                    VideoVo videoVo = new VideoVo();
                    BeanUtils.copyProperties(eduVideo,videoVo);

                    videoVos.add(videoVo);
                }
            }
            chapterVo.setList(videoVos);

        }



        return finalList;
    }

    @Override
    //根据课程id查询课程信息
    public CourseInfo getInfoByCourseId(String id) {
        EduCourse course = courseService.getById(id);
        //获取课程简介
        EduCourseDescription description = eduCourseDescriptionService.getById(course);
        //封装数据
        CourseInfo courseInfo = new CourseInfo();

        BeanUtils.copyProperties(course,courseInfo);
        courseInfo.setDescription(description.getDescription());

        return courseInfo;
    }

    @Override
    public void updateByCourseId(CourseInfo courseInfo) {
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfo,eduCourse);

        eduCourseService.update(eduCourse,null);

        EduCourseDescription  eduCourseDescription = new EduCourseDescription();
        eduCourseDescription.setDescription(courseInfo.getDescription());
        eduCourseDescriptionService.update(eduCourseDescription,null);

    }

    @Override
    public boolean deleteByVideoNumb(String id) {
        //根据课程ID查询章节小节，如果章节下面有小节则不让删除
        QueryWrapper<EduVideo> eduVideoQueryWrapper = new QueryWrapper<>();
        eduVideoQueryWrapper.eq("chapter_id",id);
        int count = videoService.count(eduVideoQueryWrapper);
        if(count>0){
            return false;
        }else {
            chapterService.removeById(id);
            return true;
        }



    }

    @Override
    public void removeByCourseId(String id) {
        QueryWrapper<EduChapter> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("course_id",id);
        baseMapper.delete(queryWrapper);
    }
}
