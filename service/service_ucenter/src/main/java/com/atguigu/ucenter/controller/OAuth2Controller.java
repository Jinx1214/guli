package com.atguigu.ucenter.controller;

import com.atguigu.Result.JwtUtils;
import com.atguigu.ucenter.Utils.ConstantUtil;
import com.atguigu.ucenter.Utils.HttpClientUtils;
import com.atguigu.ucenter.entity.Member;
import com.atguigu.ucenter.service.MemberService;
import com.google.gson.Gson;
import org.aspectj.weaver.ast.Var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;

@Controller
@RequestMapping("/api/ucenter/wx")
@CrossOrigin
public class OAuth2Controller {
    @Autowired
    private MemberService memberService;

    @GetMapping("/callback")
    public String callback(String code,String state){
        //根据code去获取openid个assess_token
        System.out.println(code);

        String baseAccessTokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token" +
                "?appid=%s" +
                "&secret=%s" +
                "&code=%s" +
                "&grant_type=authorization_code";

       String token =  String.format(
                baseAccessTokenUrl,
                ConstantUtil.APPID,
                ConstantUtil.AppSecret,
                code
        );
       //通过token获取信息
        try {
            String result = HttpClientUtils.get(token);
            //将获取到的信息进行格式转换
            Gson gson = new Gson();
            HashMap hashMap = gson.fromJson(result, HashMap.class);
            String access_token = (String) hashMap.get("access_token");
            String openid = (String) hashMap.get("openid");
            //判断数据库中 该用户id是否登陆过
          Member member =  memberService.getUserByOpenid(openid);
          if(member==null){
              //请求微信资源服务器，通过openid 获取用户信息
              //将用户信息添加到数据库中
              member = new Member();
              String baseUserInfoUrl = "https://api.weixin.qq.com/sns/userinfo" +
              "?access_token=%s" +
              "&openid=%s";
              String userInfoUrl = String.format(baseUserInfoUrl, access_token, openid);
              String UserInfo = HttpClientUtils.get(userInfoUrl);
              //解析返回的字符串
              HashMap MemberInfo = gson.fromJson(UserInfo, HashMap.class);

              member.setNickname((String) MemberInfo.get("nickname"));
              member.setAvatar((String) MemberInfo.get("headimgurl"));
              member.setOpenid(openid);
              memberService.save(member);
          }
            //将用户信息用jwt加密返回给前端浏览器地址
            String jwtToken = JwtUtils.getJwtToken(member.getId(), member.getNickname());
            return "redirect:http://localhost:3000?token="+jwtToken;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }



    }

    @GetMapping("/login")
    public String login() throws UnsupportedEncodingException {
        // 微信开放平台授权baseUrl

        String baseUrl = "https://open.weixin.qq.com/connect/qrconnect" +
        "?appid=%s" +
        "&redirect_uri=%s" +
        "&response_type=code" +
        "&scope=snsapi_login" +
        "&state=%s" +
        "#wechat_redirect";
        String redirectUrl = URLEncoder.encode(ConstantUtil.RedirectUrl,"UTF-8");

        String rUrl =  String.format(
                baseUrl,
                ConstantUtil.APPID,
                redirectUrl,
                "atguigu"
        );
        return "redirect:"+rUrl;
    }
}
