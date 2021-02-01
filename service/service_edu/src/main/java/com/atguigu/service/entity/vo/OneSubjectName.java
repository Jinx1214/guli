package com.atguigu.service.entity.vo;

import lombok.Data;

import java.util.List;

@Data
public class OneSubjectName {

    private String id;

    private String title;

    private List<TwoSubjectName> data;
}
