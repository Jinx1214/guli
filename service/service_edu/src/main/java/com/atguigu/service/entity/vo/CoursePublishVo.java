package com.atguigu.service.entity.vo;

import lombok.Data;

import java.util.List;

@Data
public class CoursePublishVo {
    private String id;

    private String title;

    private String cover;

    private Integer lessonNum;

    private String subjectLevelOne;

    private String subjectLevelTwo;

    private String teacherName;

    private String price;//只用于显示
}
