package com.atguigu.crm.controller;


import com.atguigu.Result.R;
import com.atguigu.crm.entity.Banner;
import com.atguigu.crm.service.BannerService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 首页banner表 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-01-07
 */
@RestController
@RequestMapping("/crm/banner")
@CrossOrigin
public class BannerController {

    @Autowired
    private BannerService bannerService;

    //分页获取banner
    @GetMapping("getBanner/{current}/{limit}")
    public R getBanner(@PathVariable long current,
                       @PathVariable long limit){
        Page<Banner> page = new Page<>(current,limit);
        bannerService.page(page,null);

        return R.ok().data("total",page.getTotal()).data("list",page.getRecords());
    }

    //添加banner
    @PostMapping("addBanner")
    public R addBanner(@RequestBody Banner banner){
        bannerService.save(banner);
        return R.ok();
    }

    //删除banner
    @DeleteMapping("delete/{id}")
    public R delete(@PathVariable long id){
        bannerService.removeById(id);
        return R.ok();
    }

    //编辑banner
    @PostMapping("update")
    public R update(@RequestBody Banner banner){
        bannerService.updateById(banner);
        return R.ok();
    }

    //查询单个banner
    @GetMapping("getBanner/{id}")
    public R getBanner(@PathVariable long id){
        Banner banner = bannerService.getById(id);
        return R.ok().data("banner",banner);
    }



}

