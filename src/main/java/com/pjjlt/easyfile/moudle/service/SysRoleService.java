package com.pjjlt.easyfile.moudle.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.pjjlt.easyfile.moudle.entity.SysRole;
import com.pjjlt.easyfile.moudle.model.SysRoleModel;

import java.util.List;

public interface SysRoleService extends IService<SysRole> {
    List<SysRole> getList(SysRoleModel sysRoleModel);
    //新增角色
    void create(SysRoleModel sysRoleModel);
    //分页获取角色
    Page<SysRole> getPage(SysRoleModel sysRoleModel);
    //更新角色
    void updateRole(SysRoleModel sysRoleModel) throws Exception;
    //删除角色
    void deleteRole(SysRoleModel sysRoleModel) throws Exception;
    //给用户分配角色
    void setRole(SysRoleModel sysRoleModel) throws Exception;
    //给角色分配权限
    void setMenu(SysRoleModel sysRoleModel) throws Exception;
}
