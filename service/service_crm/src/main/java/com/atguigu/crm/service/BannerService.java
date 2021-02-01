package com.atguigu.crm.service;

import com.atguigu.crm.entity.Banner;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务类
 * </p>
 *
 * @author testjava
 * @since 2021-01-07
 */
public interface BannerService extends IService<Banner> {

    List<Banner> getAll();
}
