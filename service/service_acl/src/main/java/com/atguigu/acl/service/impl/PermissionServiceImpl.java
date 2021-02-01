package com.atguigu.acl.service.impl;

import com.atguigu.acl.entity.Permission;
import com.atguigu.acl.mapper.PermissionMapper;
import com.atguigu.acl.service.PermissionService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 权限 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-01-22
 */
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {


    @Override
    public List<Permission> getAllPermission() {
        //先获取所有的课程分类
        QueryWrapper<Permission> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        List<Permission> permissions = baseMapper.selectList(wrapper);
        List<Permission> finalAll = selectOne(permissions);
        return finalAll;
    }


    private List<Permission> selectOne(List<Permission> permissions) {
        List<Permission> permissionList = new ArrayList<>();
        //获取一级菜单
        for (Permission permission : permissions) {

            if ("0".equals(permission.getPid())) {
                permission.setLevel(1);
                permissionList.add(selectNode(permission, permissions));

            }
        }
        return permissionList;
    }

    private Permission selectNode(Permission permission, List<Permission> permissions) {
        permission.setChildren(new ArrayList<Permission>());

        //将子id与父id进行比较
        for (Permission it : permissions) {
            if (permission.getId().equals(it.getPid())) {
                int level = permission.getLevel() + 1;
                it.setLevel(level);
                if (permission.getChildren() == null) {
                    permission.setChildren(new ArrayList<Permission>());
                }
                permission.getChildren().add(selectNode(it, permissions));
            }

        }
        return permission;
    }


    //删除菜单接口
    @Override
    public void removeChinderById(String id) {

        List<String> ids = new ArrayList<>();
        //利用递归获取所有子id
        this.selectAllId(id, ids);
        ids.add(id);
        baseMapper.deleteBatchIds(ids);

    }

    private void selectAllId(String id, List<String> ids) {
        QueryWrapper<Permission> wrapper = new QueryWrapper<>();

        wrapper.eq("pid", id);
        wrapper.select("id");
        List<Permission> permissions = baseMapper.selectList(wrapper);
        permissions.stream().forEach(item -> {
                ids.add(item.getId());
                this.selectAllId(item.getId(), ids);
        });
    }
}





