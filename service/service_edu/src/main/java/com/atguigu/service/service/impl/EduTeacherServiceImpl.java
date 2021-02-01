package com.atguigu.service.service.impl;

import com.atguigu.service.entity.EduTeacher;
import com.atguigu.service.mapper.EduTeacherMapper;
import com.atguigu.service.service.EduTeacherService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-12-26
 */
@Service
public class EduTeacherServiceImpl extends ServiceImpl<EduTeacherMapper, EduTeacher> implements EduTeacherService {

    @Override
    public Map<String, Object> findByPage(Page<EduTeacher> page) {
        //进行分页
        QueryWrapper<EduTeacher> eduTeacherQueryWrapper = new QueryWrapper<>();

        baseMapper.selectPage(page,eduTeacherQueryWrapper);
        boolean hasNext = page.hasNext();
        long total = page.getTotal();
        List<EduTeacher> records = page.getRecords();
        long current = page.getCurrent();
        long size = page.getSize();
        long pages = page.getPages();
        boolean hasPrevious = page.hasPrevious();
        Map<String,Object> map = new HashMap<>();
        map.put("items", records);

        map.put("current", current);

        map.put("pages", pages);

        map.put("size", size);

        map.put("total", total);

        map.put("hasNext", hasNext);
        map.put("hasPrevious", hasPrevious);


        return map;
    }
}
