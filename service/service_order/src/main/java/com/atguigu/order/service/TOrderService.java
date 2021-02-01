package com.atguigu.order.service;

import com.atguigu.order.entity.TOrder;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 订单 服务类
 * </p>
 *
 * @author testjava
 * @since 2021-01-18
 */
public interface TOrderService extends IService<TOrder> {

    String createrOrderNo(String id, HttpServletRequest request);

    TOrder getByCourseId(String id);
}
