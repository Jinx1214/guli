package com.atguigu.order.controller;


import com.atguigu.Result.R;
import com.atguigu.order.entity.TOrder;
import com.atguigu.order.service.TOrderService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 订单 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-01-18
 */
@RestController
@RequestMapping("/order/order")
@CrossOrigin
public class TOrderController {

    @Autowired
    private TOrderService orderService;


    //通过课程id创建订单号
    @GetMapping("orderNo/{id}")
    public R createOrder(@PathVariable String id, HttpServletRequest request){
        String orderNo =  orderService.createrOrderNo(id,request);


        return R.ok().data("orderNo",orderNo);
    }


    //通过订单号查询课程信息

    @GetMapping("courseInfo/{id}")
    public R getOrder(@PathVariable String id){
        //通过订单号查询课程信息
        TOrder serviceById = orderService.getByCourseId(id);

        return R.ok().data("courseInfo",serviceById);
    }

    //通过用户id和课程id查询用户是否购买了课程
    @GetMapping("getCoursePayStatus/{courseId}/{memeberId}")
    public boolean geCourseInfo(@PathVariable String courseId,
                          @PathVariable String memeberId){
        QueryWrapper<TOrder> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("course_id",courseId);
        queryWrapper.eq("member_id",memeberId);
        queryWrapper.eq("status",1);
        int count = orderService.count(queryWrapper);
        return count>0;
    }

}

