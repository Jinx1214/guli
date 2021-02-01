package com.atguigu.order.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.atguigu.order.entity.TOrder;
import com.atguigu.order.entity.TPayLog;
import com.atguigu.order.mapper.TPayLogMapper;
import com.atguigu.order.service.TOrderService;
import com.atguigu.order.service.TPayLogService;
import com.atguigu.order.utils.HttpClient;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.wxpay.sdk.WXPayUtil;
import org.aspectj.weaver.ast.Var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 支付日志表 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-01-18
 */
@Service
public class TPayLogServiceImpl extends ServiceImpl<TPayLogMapper, TPayLog> implements TPayLogService {


    @Autowired
    private TOrderService tOrderService;
    //根据订单号生成支付二维码
    @Override
    public Map createPay(String id) {
        QueryWrapper<TOrder> wrapper = new QueryWrapper<>();
        wrapper.eq("order_no",id);
        TOrder order = tOrderService.getOne(wrapper);
        //将生成二维码的参数设置进map中
        Map m = new HashMap();

        try {
            //1、设置支付参数
            m.put("appid", "wx74862e0dfcf69954");
            m.put("mch_id", "1558950191");
            m.put("nonce_str", WXPayUtil.generateNonceStr());
            m.put("body", order.getCourseTitle());
            m.put("out_trade_no", id);
            m.put("total_fee", order.getTotalFee().multiply(new BigDecimal("100")).longValue()+"");
            m.put("spbill_create_ip", "127.0.0.1");
            m.put("notify_url", "http://guli.shop/api/order/weixinPay/weixinNotify");
            m.put("trade_type", "NATIVE");
            //2、HTTPClient来根据URL访问第三方接口并且传递参数
            HttpClient client = new HttpClient("https://api.mch.weixin.qq.com/pay/unifiedorder");
              //client设置参数
            client.setXmlParam(WXPayUtil.generateSignedXml(m, "T6m9iK73b0kn9g5v426MKfHQH7X8rKwb"));
            client.setHttps(true);
            client.post();
             //3、返回第三方的数据
            String xml = client.getContent();
            Map<String, String> resultMap = WXPayUtil.xmlToMap(xml);
             //4、封装返回结果集
            Map map = new HashMap<>();
            map.put("out_trade_no", id);
            map.put("course_id", order.getCourseId());
            map.put("total_fee", order.getTotalFee());
            map.put("result_code", resultMap.get("result_code"));
            map.put("code_url", resultMap.get("code_url"));
            //微信支付二维码2小时过期，可采取2小时未支付取消订单
            //redisTemplate.opsForValue().set(orderNo, map, 120, TimeUnit.MINUTES);
            return map;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Map getPayLog(String id) {

        Map m = new HashMap<>();
        m.put("appid", "wx74862e0dfcf69954");
        m.put("mch_id", "1558950191");
        m.put("out_trade_no", id);
        m.put("nonce_str", WXPayUtil.generateNonceStr());

        //httpclien客户端发送请求 查询是否付款  微信接口查询必需使用xml格式 ，所以这里将map格式转换成xml格式
        HttpClient httpClient = new HttpClient("https://api.mch.weixin.qq.com/pay/orderquery");
        httpClient.setHttps(true);
        try {
            httpClient.setXmlParam(WXPayUtil.generateSignedXml(m,"T6m9iK73b0kn9g5v426MKfHQH7X8rKwb"));
            httpClient.post();
            //获取查询后内容 ，将数据进行封装返回
            //返回回来的是xml格式，我们需要将它转换成map格式
            String xml = httpClient.getContent();
            System.out.println(xml);
            Map<String, String> resultMap = WXPayUtil.xmlToMap(xml);
            return resultMap;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void updatePayLog(Map map) {
        //根据订单号查询交易状态
        QueryWrapper<TOrder> wrapper = new QueryWrapper<>();
        wrapper.eq("order_no",map.get("out_trade_no"));
        TOrder order = tOrderService.getOne(wrapper);
        System.out.println("===============order"+order);
        if(order.getStatus().intValue()==1){return;}

        order.setStatus(1);
        tOrderService.updateById(order);

        //向订单表中插入数据
        TPayLog payLog = new TPayLog();
        payLog.setOrderNo(order.getOrderNo());//支付订单号
        payLog.setPayTime(new Date());
        payLog.setPayType(1);//支付类型
        payLog.setTotalFee(order.getTotalFee());//总金额(分)
        payLog.setTradeState((String) map.get("trade_state"));//支付状态
        payLog.setTransactionId((String)map.get("transaction_id"));
        payLog.setAttr(JSONObject.toJSONString(map));
        baseMapper.insert(payLog);//插入到支付日志表
    }

}
