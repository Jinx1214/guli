package com.atguigu.service.service.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.read.listener.ReadListener;
import com.atguigu.service.entity.EduSubject;
import com.atguigu.service.entity.excel.EduSubectData;
import com.atguigu.service.service.EduSubjectService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

public class SubjectListener extends AnalysisEventListener<EduSubectData> {

   private SubjectListener(){}
   public   EduSubjectService Eduservice;
   public SubjectListener(EduSubjectService service){
       this.Eduservice = service;
   }


    @Override
    public void invoke(EduSubectData eduSubectData, AnalysisContext analysisContext) {
        //判断一级目录是否在数据库中存在，如果存在则不读取
        //判断文件中是否有数据
        if(eduSubectData ==null){
            throw new RuntimeException();
        }
        EduSubject eduSubject = oneSubect(Eduservice,eduSubectData.getOneName());
        if(eduSubject == null){
            //如果不存在 将excel的表头行进行读取 然后在数据库中进行创建
            eduSubject = new EduSubject();
            eduSubject.setParentId("0");
            eduSubject.setTitle(eduSubectData.getOneName());
            Eduservice.save(eduSubject);
        }
        String parentId = eduSubject.getId();
        //判断二级目录是否存在
        EduSubject twoSub = twoSubect(Eduservice,eduSubectData.getTwoName(),parentId);
        if(twoSub == null){
            //如果不存在 将excel的表头行进行读取 然后在数据库中进行创建 二级目录的父id为一级目录的id
            twoSub = new EduSubject();
            twoSub.setParentId(parentId);
            twoSub.setTitle(eduSubectData.getOneName());
            Eduservice.save(twoSub);
        }


    }

    private EduSubject oneSubect(EduSubjectService Eduservice,String name){
        QueryWrapper<EduSubject> wrapper = new QueryWrapper();
        wrapper.eq("title",name);
        wrapper.eq("parent_id","0");
        EduSubject one = Eduservice.getOne(wrapper);
        return one;
    }

    private EduSubject twoSubect(EduSubjectService Eduservice,String name,String pid){
        QueryWrapper<EduSubject> wrapper = new QueryWrapper();
        wrapper.eq("title",name);
        wrapper.eq("parent_id",pid);
        EduSubject two = Eduservice.getOne(wrapper);
        return two;
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
