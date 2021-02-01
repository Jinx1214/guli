package com.atguigu.service.service.impl;

import com.alibaba.excel.EasyExcel;
import com.atguigu.service.entity.EduSubject;
import com.atguigu.service.entity.excel.EduSubectData;
import com.atguigu.service.entity.vo.OneSubjectName;
import com.atguigu.service.entity.vo.TwoSubjectName;
import com.atguigu.service.mapper.EduSubjectMapper;
import com.atguigu.service.service.EduSubjectService;
import com.atguigu.service.service.listener.SubjectListener;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-12-30
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {

    @Autowired
    private EduSubjectService service;
    @Override
    public void addSubject(MultipartFile file) {
        //读取上传来的文件
        //获取文件的输入流
        try {
            InputStream in = file.getInputStream();
            //进行文件的读取
            EasyExcel.read(in, EduSubectData.class,new SubjectListener(service)).sheet().doRead();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    //获取所有一级标签和子标签
    public List<OneSubjectName> getAllSubjectList() {
        //获取所有一级分类
        List<OneSubjectName> oneSubjectNames = new ArrayList<>();
        QueryWrapper<EduSubject> oneWrapper = new QueryWrapper<>();
        oneWrapper.eq("parent_id","0");
        List<EduSubject> oneEduSubject = baseMapper.selectList(oneWrapper);


       //获取 所有二级分类
        List<OneSubjectName> twoSubjectNames = new ArrayList<>();
        QueryWrapper<EduSubject> twoWrapper = new QueryWrapper<>();
        oneWrapper.ne("parent_id","0");
        List<EduSubject> twoEduSubject = baseMapper.selectList(twoWrapper);


        //封装一级分类
        for (int i = 0; i < oneEduSubject.size(); i++) {

            EduSubject eduSubject = oneEduSubject.get(i);
            OneSubjectName oneSubjectName = new OneSubjectName();
            BeanUtils.copyProperties(eduSubject,oneSubjectName);
            List<TwoSubjectName> tw  = new ArrayList<>();
            //封装二级分分类
            for (int j = 0; j < twoEduSubject.size(); j++) {
                EduSubject twoEdu =  twoEduSubject.get(j);
                if(twoEdu.getParentId().equals( oneSubjectName.getId())){
                    TwoSubjectName twoSubjectName =new TwoSubjectName();
                    BeanUtils.copyProperties(twoEdu,twoSubjectName);
                    tw.add(twoSubjectName);
                }
                oneSubjectName.setData(tw);
            }
            oneSubjectNames.add(oneSubjectName);
        }

        return oneSubjectNames;
    }
}
