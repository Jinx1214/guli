package com.atguigu.acl.service;

import com.atguigu.acl.entity.Permission;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 权限 服务类
 * </p>
 *
 * @author testjava
 * @since 2021-01-22
 */
public interface PermissionService extends IService<Permission> {

    //获取所有菜单接口
    List<Permission> getAllPermission();

    void removeChinderById(String id);
}
