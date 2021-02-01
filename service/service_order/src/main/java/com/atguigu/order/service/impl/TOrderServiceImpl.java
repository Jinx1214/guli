package com.atguigu.order.service.impl;

import com.atguigu.Result.JwtUtils;
import com.atguigu.Result.webVo.CoursePublishVoOrder;
import com.atguigu.Result.webVo.Memberorder;
import com.atguigu.order.entity.TOrder;
import com.atguigu.order.mapper.TOrderMapper;
import com.atguigu.order.service.TOrderService;
import com.atguigu.order.utils.OrderNoUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-01-18
 */
@Service
public class TOrderServiceImpl extends ServiceImpl<TOrderMapper, TOrder> implements TOrderService {
    @Autowired
    private com.atguigu.order.cliet.courseClient courseClient;

    @Autowired
    private com.atguigu.order.cliet.memberClient memberClient;

    @Override
    public String createrOrderNo(String id, HttpServletRequest request) {
        //通过课程id创建订单号，在创建订单号的同时，将会员信息和课程信息存入订单表中
        CoursePublishVoOrder courseInfoOrder = courseClient.getCourseInfoOrder(id);
        //通过request中的 token 获取用户id信息
        String token = JwtUtils.getMemberIdByJwtToken(request);
        System.out.println(token);
        Memberorder memberorder = memberClient.userInfovo(token);

        //将用户信息存入表中 并生成唯一的uuid返回
        TOrder tOrder = new TOrder();
        tOrder.setOrderNo(OrderNoUtil.getOrderNo());
        tOrder.setCourseCover(courseInfoOrder.getCover());
        tOrder.setCourseTitle(courseInfoOrder.getTitle());
        tOrder.setMemberId(memberorder.getId());
        tOrder.setCourseId(id);
        tOrder.setTotalFee(courseInfoOrder.getPrice());
        tOrder.setTeacherName(courseInfoOrder.getTeacherName());
        tOrder.setPayType(1);
        tOrder.setNickname(memberorder.getNickname());
        tOrder.setMobile(memberorder.getMobile());
        tOrder.setIsDeleted(0);
        tOrder.setStatus(0);
        System.out.println(tOrder);
        baseMapper.insert(tOrder);


        return tOrder.getOrderNo();
    }

    @Override
    public TOrder getByCourseId(String id) {
        //根据订单id 查询 订单信息
        QueryWrapper<TOrder> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("order_no",id);
        TOrder tOrder = baseMapper.selectOne(queryWrapper);
        return tOrder;
    }
}
