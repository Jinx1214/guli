package com.atguigu.service.entity.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
//章节Vo类
public class ChapterVo {
    private String id;

    private String title;


    private List<VideoVo> list;

}
