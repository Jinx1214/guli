package com.atguigu.service.service;

import com.atguigu.service.entity.EduSubject;
import com.atguigu.service.entity.vo.OneSubjectName;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author testjava
 * @since 2020-12-30
 */
public interface EduSubjectService extends IService<EduSubject> {

    void addSubject(MultipartFile file);

    List<OneSubjectName> getAllSubjectList();
}
