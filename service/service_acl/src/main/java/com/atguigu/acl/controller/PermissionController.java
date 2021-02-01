package com.atguigu.acl.controller;


import com.atguigu.Result.R;
import com.atguigu.acl.entity.Permission;
import com.atguigu.acl.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 权限 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-01-22
 */
@RestController
@RequestMapping("/acl/permission")
public class PermissionController {

    @Autowired
    private PermissionService service;

    //获取所有菜单接口
    @GetMapping("getAllPermission")
    public R getAllPermission(){
        List<Permission> permissions =  service.getAllPermission();

        return R.ok().data("list",permissions);
    }

    //删除菜单接口
    @DeleteMapping("deletePermission/{id}")
    public R deletePermission(@PathVariable String id){
        service.removeChinderById(id);
        return R.ok();
    }

}

