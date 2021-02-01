package com.atguigu.crm.service.impl;

import com.atguigu.crm.entity.Banner;
import com.atguigu.crm.mapper.BannerMapper;
import com.atguigu.crm.service.BannerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-01-07
 */
@Service
public class BannerServiceImpl extends ServiceImpl<BannerMapper, Banner> implements BannerService {

    @Override
    public List<Banner> getAll() {
        List<Banner> banners = baseMapper.selectList(null);
        return banners;
    }
}
