package com.atguigu.ucenter.controller;


import com.atguigu.Result.JwtUtils;
import com.atguigu.Result.R;
import com.atguigu.Result.webVo.Memberorder;
import com.atguigu.ucenter.entity.Member;
import com.atguigu.ucenter.entity.VO.RegisterVO;
import com.atguigu.ucenter.service.MemberService;
import com.netflix.client.http.HttpRequest;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-01-12
 */
@RestController
@RequestMapping("/ucenter/member")
@CrossOrigin
public class MemberController {


    @Autowired
    private MemberService memberService;
    //注册接口
    @PostMapping("/register")
    public R register(@RequestBody RegisterVO registerVO){
        memberService.register(registerVO);
        return R.ok();
    }


    //登陆页面
    @PostMapping    ("/login")
    public R login(@RequestBody Member member){
        String token =  memberService.login(member);
        return R.ok().data("token",token);
    }
    //根据token获取用户参数
    @GetMapping("/userInfo")
    public R userInfo(HttpServletRequest request){
        String idToken = JwtUtils.getMemberIdByJwtToken(request);
        //根据token中的id获取数据
        Member memberServiceById = memberService.getById(idToken);
        return R.ok().data("userInfo",memberServiceById);
    }

    @GetMapping("/userInfo/{id}")
    public Memberorder userInfovo(@PathVariable String id){
        //根据token中的id获取数据
        Member memberServiceById = memberService.getById(id);
        Memberorder memberorder = new Memberorder();
        BeanUtils.copyProperties(memberServiceById,memberorder);
        return memberorder;
    }
    //统计数据接口
    @GetMapping("/getStaInfo/{day}")
    public  R getStaInfo(@PathVariable String day){
        //通过日期获取当日的注册人数
        Integer count = memberService.getStaInfo(day);
        return R.ok().data("count",count);

    }

}

