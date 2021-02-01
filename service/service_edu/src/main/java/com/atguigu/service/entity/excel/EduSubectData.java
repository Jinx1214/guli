package com.atguigu.service.entity.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class EduSubectData {
    @ExcelProperty(index = 0)
    private String oneName;
    @ExcelProperty(index = 1)
    private String twoName;
}
