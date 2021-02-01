package com.atguigu.service.entity;

import lombok.Data;

@Data
public class TeacherQuery {
    //教师名称
    private String name;

    //教师等级
    private Integer level;

    //开始的时间
    private String gmtCreat;

    //结束的时间
    private String gmtEnd;

}
