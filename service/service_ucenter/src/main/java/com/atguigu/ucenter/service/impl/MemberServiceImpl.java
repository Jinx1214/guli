package com.atguigu.ucenter.service.impl;

import com.atguigu.Result.JwtUtils;
import com.atguigu.Result.MD5;
import com.atguigu.ucenter.entity.Member;
import com.atguigu.ucenter.entity.VO.RegisterVO;
import com.atguigu.ucenter.mapper.MemberMapper;
import com.atguigu.ucenter.service.MemberService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-01-12
 */
@Service
public class MemberServiceImpl extends ServiceImpl<MemberMapper, Member> implements MemberService {

    @Autowired
    private RedisTemplate<String,String> redisTemplate;
    //注册接口
    @Override
    public void register(RegisterVO registerVO) {
        String code = registerVO.getCode();
        String password = registerVO.getPassword();
        String nickName = registerVO.getNickName();
        String phone = registerVO.getPhone();

        String redisCode = redisTemplate.opsForValue().get(phone);

        if(!code.equals(redisCode)){
            throw  new RuntimeException();
        }
        QueryWrapper<Member> memberQueryWrapper= new QueryWrapper<>();
        memberQueryWrapper.select("mobile");
        Integer integer = baseMapper.selectCount(memberQueryWrapper);
        if(integer>0){
            throw  new RuntimeException();
        }

        Member member = new Member();
        member.setPassword(MD5.encrypt(password));
        member.setNickname(nickName);
        member.setMobile(phone);
        member.setSex(1);
        member.setAge(0);
        member.setAvatar("https://online-teach-file.oss-cn-beijing.aliyuncs.com/teacher/2019/11/07/91871e25-fd83-4af6-845f-ea8d471d825d.png");
        baseMapper.insert(member);
    }

    @Override
    public String login(Member member) {
        //获取传递过来的参数
        String mobile = member.getMobile();
        String password = member.getPassword();
        System.out.println("password"+password);
        if(StringUtils.isEmpty(member)|| StringUtils.isEmpty(password)){
            throw new RuntimeException();
        }
        //进行对比
        QueryWrapper<Member> memberQueryWrapper = new QueryWrapper<>();
        memberQueryWrapper.eq("mobile",mobile);
        Member member1 = baseMapper.selectOne(memberQueryWrapper);
        System.out.println(member1);

        if(!member1.getPassword().equals(MD5.encrypt(password))){
            throw new RuntimeException();
        }

        String jwtToken = JwtUtils.getJwtToken(member1.getId(), member1.getNickname());

        return jwtToken;
    }

    @Override
    public Member getUserByOpenid(String openid) {
        QueryWrapper<Member> memberQueryWrapper = new QueryWrapper<>();
        memberQueryWrapper.eq("openid",openid);
        Member member = baseMapper.selectOne(memberQueryWrapper);
        return member;
    }

    @Override
    public Integer getStaInfo(String day) {
        Integer count = baseMapper.geStaInfo(day);
        return count;
    }
}
