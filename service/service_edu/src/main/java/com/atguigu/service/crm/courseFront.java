package com.atguigu.service.crm;

import com.alibaba.excel.event.Order;
import com.atguigu.Result.JwtUtils;
import com.atguigu.Result.R;
import com.atguigu.Result.webVo.CoursePublishVoOrder;
import com.atguigu.service.client.OrderClient;
import com.atguigu.service.entity.EduCourse;
import com.atguigu.service.entity.EduSubject;
import com.atguigu.service.entity.vo.ChapterVo;
import com.atguigu.service.entity.vo.CourseSearchVO;
import com.atguigu.service.entity.vo.CourseWebVo;
import com.atguigu.service.entity.vo.OneSubjectName;
import com.atguigu.service.service.EduChapterService;
import com.atguigu.service.service.EduCourseService;
import com.atguigu.service.service.EduSubjectService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RequestMapping("/eduservice/coursefron")
@RestController
@CrossOrigin
public class courseFront {
    @Autowired
    private EduChapterService eduChapterService;
    @Autowired
    private OrderClient client;

    @Autowired
    private EduSubjectService subjectService;
    @Autowired
    private EduCourseService courseService;
    //前端分页查询获取课程
    @PostMapping("/course/{current}/{limit}")
    public R getCourseList(@PathVariable long current,
                           @PathVariable long limit,
                           @RequestBody(required = false) CourseSearchVO courseSearchVO){
       Page<EduCourse> page = new Page<>(current,limit);
       Map map =  courseService.getCourseList(page,courseSearchVO);

        return R.ok().data(map);
    }
    //前端获取所有一级分类
    @GetMapping("/subjectList")
    public R getSubjectList(){
        List<OneSubjectName> allSubjectList = subjectService.getAllSubjectList();

        return R.ok().data("subjectList",allSubjectList);
    }
    //根据课程id 获取课程页面详情参数
    @GetMapping("courseFronInfo/{id}")
    public R getCourseInfo(@PathVariable String id, HttpServletRequest request){
        CourseWebVo courseWebVo =  courseService.getCourseInfo(id);
        //根据课程id 获取课程大纲
        List<ChapterVo> allChapterList = eduChapterService.getAllChapterList(id);
        //根据用户ID和课程ID获取该用户是否购买过课程
        //代表已经登陆
        if(!(request.getHeader("token")==null)){
            //通过用户id和课程id 查询对应的支付记录，如果表中status状态为1 则表示已经购买过，由于一个课程存在多次订单，这里用count来查找已经支付了的订单更合适
            boolean flag = client.geCourseInfo(id, JwtUtils.getMemberIdByJwtToken(request));
            //如果是true，则表示已经购买
            return R.ok().data("courseWebVo",courseWebVo).data("allChapterList",allChapterList).data("isBuy",flag);
        }
        return R.ok().data("courseWebVo",courseWebVo).data("allChapterList",allChapterList);
    }

    @GetMapping("courseInfo/{id}")
    public CoursePublishVoOrder getCourseInfoOrder(@PathVariable String id){
        //根据课程id 获取课程信息
        CourseWebVo courseWebVo =  courseService.getCourseInfo(id);
        CoursePublishVoOrder coursePublishVoOrder = new CoursePublishVoOrder();
        BeanUtils.copyProperties(courseWebVo,coursePublishVoOrder);
        return coursePublishVoOrder;
    }



}
