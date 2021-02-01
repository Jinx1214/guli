package com.atguigu.service.service.impl;

import com.atguigu.service.client.VodClient;
import com.atguigu.service.entity.EduCourse;
import com.atguigu.service.entity.EduCourseDescription;
import com.atguigu.service.entity.EduVideo;
import com.atguigu.service.entity.vo.*;
import com.atguigu.service.mapper.EduCourseMapper;
import com.atguigu.service.service.EduChapterService;
import com.atguigu.service.service.EduCourseDescriptionService;
import com.atguigu.service.service.EduCourseService;
import com.atguigu.service.service.EduVideoService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-12-31
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {

    @Autowired
    private EduCourseDescriptionService descriptionService;

    @Autowired
    private EduVideoService videoService;

    @Autowired
    private VodClient client;

    @Autowired
    private EduChapterService eduChapterService;


    @Override
    public String saveCourse(CourseInfo courseInfo) {
        //获取传递过来的参数 将值赋值给Course对象进行保存
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfo,eduCourse);

        baseMapper.insert(eduCourse);

        //将课程描述的id设置为和课程一样的ID
        String cid = eduCourse.getId();
        EduCourseDescription description = new EduCourseDescription();
        description.setDescription(courseInfo.getDescription());
        description.setId(cid);
        descriptionService.save(description);
        return cid;
    }

    @Override
    public CoursePublishVo getCourse(String id) {
        CoursePublishVo coursePublish = baseMapper.getCoursePublish(id);
        return coursePublish;
    }

    @Override
    public void removeByCourseId(String id) {
        //根据课程id获取所有视频id
        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",id);
        //这个是为了只获取video_source_id字段的值
        wrapper.select("video_source_id");
        List<EduVideo> list = videoService.list(wrapper);
        //将获取到的小节信息进行遍历 将视频id进行封装
        List<String> Ids = new ArrayList<>();
        for (EduVideo video : list) {
            if(video.getVideoSourceId()!=null){
                Ids.add(video.getVideoSourceId());
            }
        }
        //调用远程服务进行删除
        if(Ids!=null){
            client.delete(Ids);
        }

        //根据课程ID删除小节信息
        videoService.removeByCourseId(id);

        //根据课程id删除描述信息
        descriptionService.removeByCourseId(id);




        //根据课程删除章节信息
        eduChapterService.removeByCourseId(id);
        //根据课程id删除课程
        int i = baseMapper.deleteById(id);
        if(i==0){
            throw new RuntimeException();
        }

    }

    @Override
    public Map getCourseList(Page<EduCourse> page, CourseSearchVO courseSearchVO) {
        System.out.println(courseSearchVO);


        QueryWrapper<EduCourse> queryWrapper = new QueryWrapper<>();
        //判断条件查询，动态拼接sql语句
        if(!StringUtils.isEmpty(courseSearchVO.getSubjectParentId())){
            queryWrapper.eq("subject_parent_id",courseSearchVO.getSubjectParentId());
        }
        if(!StringUtils.isEmpty(courseSearchVO.getSubjectId())){
            queryWrapper.eq("subject_id",courseSearchVO.getSubjectId());
        }
        if(!StringUtils.isEmpty(courseSearchVO.getBuyCountSort())){
            queryWrapper.orderByDesc("buy_count",courseSearchVO.getBuyCountSort());
        }
        if(!StringUtils.isEmpty(courseSearchVO.getGmtCreateSort())){
            queryWrapper.orderByDesc("gmt_create",courseSearchVO.getGmtCreateSort());
        }
        if(!StringUtils.isEmpty(courseSearchVO.getPriceSort())){
            queryWrapper.orderByDesc("price",courseSearchVO.getPriceSort());
        }
        baseMapper.selectPage(page,queryWrapper);

        List<EduCourse> records = page.getRecords();
        long current = page.getCurrent();
        long total = page.getTotal();
        long size = page.getSize();
        boolean hasNext = page.hasNext();
        boolean hasPrevious = page.hasPrevious();

        Map<String,Object> map = new HashMap<>();
        map.put("records",records);
        map.put("current",current);
        map.put("total",total);
        map.put("size",size);
        map.put("hasNext",hasNext);
        map.put("hasPrevious",hasPrevious);

        return map;
    }

    @Override
    public CourseWebVo getCourseInfo(String id) {
        CourseWebVo courseWebVo =  baseMapper.getCourseInfo(id);
        return courseWebVo;
    }

}
