package com.pjjlt.easyfile.moudle.service;

import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.pjjlt.easyfile.moudle.entity.SysMenu;
import com.pjjlt.easyfile.moudle.model.SysMenuModel;

import java.util.List;

public interface SysMenuService extends IService<SysMenu> {
    //获取sysMenu列表
    List<SysMenu> getList(SysMenuModel sysMenuModel);
    //新建权限菜单
    void create(SysMenuModel sysRoleModel);
    //更新权限菜单
    void updateMenu(SysMenuModel sysMenuModel) throws Exception;
    //删除菜单权限
    void deleteMenu(SysMenuModel sysMenuModel) throws Exception;
    //获得权限树
    List<JSONObject> tree(SysMenuModel sysMenuModel) throws Exception;
}
