package com.atguigu.order.controller;


import com.atguigu.Result.R;
import com.atguigu.order.service.TPayLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 支付日志表 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-01-18
 */
@RestController
@RequestMapping("/order/payLog")
@CrossOrigin
public class TPayLogController {
    @Autowired
    private TPayLogService tPayLogService ;

    //根据订单号生成二维码
    @GetMapping("createPay/{id}")
    public R createPay(@PathVariable String id){
        Map map =  tPayLogService.createPay(id);
        return R.ok().data(map);
    }

    //根据订单号查询支付状态
    @GetMapping("getPayLog/{id}")
    public R getPayLog(@PathVariable String id){
        Map map =  tPayLogService.getPayLog(id);
        //如果查询的集合没内容则表示支付出错
        System.out.println("=======map++++"+map);
        //如果返回的map集合中 返回支付成功，则调用接口对订单表中插入数据，还有修改订单状态
        if(map==null){
            return null;
        }
        if (map.get("trade_state").equals("SUCCESS")){
            tPayLogService.updatePayLog(map);
            return R.ok().message("支付成功");
        }


        return R.ok().code(25000).message("支付中");
    }

}

