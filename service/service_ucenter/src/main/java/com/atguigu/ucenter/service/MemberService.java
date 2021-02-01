package com.atguigu.ucenter.service;

import com.atguigu.ucenter.entity.Member;
import com.atguigu.ucenter.entity.VO.RegisterVO;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author testjava
 * @since 2021-01-12
 */
public interface MemberService extends IService<Member> {

    void register(RegisterVO registerVO);

    String login(Member member);

    Member getUserByOpenid(String openid);

    Integer getStaInfo(String day);
}
