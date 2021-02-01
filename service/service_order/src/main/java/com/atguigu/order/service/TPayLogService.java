package com.atguigu.order.service;

import com.atguigu.order.entity.TPayLog;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 支付日志表 服务类
 * </p>
 *
 * @author testjava
 * @since 2021-01-18
 */
public interface TPayLogService extends IService<TPayLog> {

    Map createPay(String id);

    Map getPayLog(String id);

    void updatePayLog(Map id);
}
