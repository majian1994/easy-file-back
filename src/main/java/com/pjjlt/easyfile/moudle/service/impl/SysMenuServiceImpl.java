package com.pjjlt.easyfile.moudle.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pjjlt.easyfile.constant.ConstantData;
import com.pjjlt.easyfile.moudle.entity.SysMenu;
import com.pjjlt.easyfile.moudle.entity.SysRoleMenu;
import com.pjjlt.easyfile.moudle.entity.SysUser;
import com.pjjlt.easyfile.moudle.mapper.SysMenuMapper;
import com.pjjlt.easyfile.moudle.model.SysMenuModel;
import com.pjjlt.easyfile.moudle.service.SysMenuService;
import com.pjjlt.easyfile.moudle.service.SysRoleMenuService;
import com.pjjlt.easyfile.security.SecurityUserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {

    @Autowired
    SysRoleMenuService sysRoleMenuService;


    @Override
    public List<SysMenu> getList(SysMenuModel sysMenuModel) {
        return baseMapper.getList(sysMenuModel);
    }
    /**
     * 新建权限菜单
     * */
    @Override
    public void create(SysMenuModel sysMenuModel) {
        sysMenuModel.pre();
        SysMenu sysMenu = new SysMenu();
        BeanUtil.copyProperties(sysMenuModel,sysMenu, ConstantData.id);
        baseMapper.insert(sysMenu);
    }
    /**
     * 更新权限菜单
     * 菜单名称、显示顺序、请求地址、菜单类型、菜单状态、权限标识、菜单图标、备注
     * */
    @Override
    public void updateMenu(SysMenuModel sysMenuModel) throws Exception{
        SysMenu sysMenu = baseMapper.selectById(sysMenuModel.getId());
        if (Objects.isNull(sysMenu)){
            throw new Exception("此菜单不存在!");
        }
        sysMenu.setMenuName(sysMenuModel.getMenuName()).setOrderNum(sysMenuModel.getOrderNum())
                .setUrl(sysMenuModel.getUrl()).setMenuType(sysMenuModel.getMenuType())
                .setVisible(sysMenuModel.getVisible()).setPerms(sysMenuModel.getPerms()).setIcon(sysMenuModel.getIcon())
                .setRemark(sysMenuModel.getRemark())
                .setModifyTime(LocalDateTime.now());
        baseMapper.updateById(sysMenu);
    }
    /**
     * 删除菜单权限
     * 级联删除 sys_role_menu
     * */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteMenu(SysMenuModel sysMenuModel) throws Exception {
        SysMenu sysMenu = baseMapper.selectById(sysMenuModel.getId());
        if (Objects.isNull(sysMenu)){
            throw new Exception("此菜单不存在!");
        }
        LambdaQueryWrapper<SysRoleMenu> lambdaQueryWrapper
                = Wrappers.<SysRoleMenu>lambdaQuery().eq(SysRoleMenu::getMenuId, sysMenu.getId());
        sysRoleMenuService.remove(lambdaQueryWrapper);
        baseMapper.deleteById(sysMenu);
    }
    /**
     * 获取菜单树
     * 目前只支持菜单名称的模糊搜索
     * */
    @Override
    public List<JSONObject> tree(SysMenuModel sysMenuModel) throws Exception{
        List<JSONObject> result = new ArrayList<>();
        SysUser currentUser = SecurityUserUtil.getCurrentUser();
        if (Objects.isNull(currentUser)){
            throw new Exception("当前用户不存在");
        }
        //如果不是超级管理员，就需要传入id
        if (!currentUser.getUserType()){
            sysMenuModel.setUserId(currentUser.getId());
        }
        //先查询出来符合条件的List<Menu>
        List<SysMenu> sysMenuList = baseMapper.getList(sysMenuModel);
        List<SysMenu> sysMenuParentList = new ArrayList<>();
        //查找所有根节点 parentId = 0,或者在集合中没有父节点的
        for (int i=0;i<sysMenuList.size();i++){
            SysMenu menuI = sysMenuList.get(i);
            if (menuI.getParentId() == 0){
                JSONObject node = new JSONObject();
                node.putOpt(ConstantData.id,menuI.getId());
                node.putOpt(ConstantData.menuName,menuI.getMenuName());
                result.add(node);
                sysMenuParentList.add(menuI);
                continue;
            }
            boolean isParent = true;
            for (int j=0;j<sysMenuList.size();j++){
                SysMenu menuJ = sysMenuList.get(j);
                if (menuI.getParentId() == menuJ.getId()){
                    isParent=false;
                    break;
                }
            }
            if (isParent){
                JSONObject node = new JSONObject();
                node.putOpt(ConstantData.id,menuI.getId());
                node.putOpt(ConstantData.menuName,menuI.getMenuName());
                result.add(node);
                sysMenuParentList.add(menuI);
            }
        }
        //递归查询子节点
        //去掉父节点们
        sysMenuList.removeAll(sysMenuParentList);
        for (JSONObject node:result){
            getMenuChildren(node,sysMenuList);
        }
        return result;
    }


    /**
     * 递归添加menu孩子
     * */
    private void getMenuChildren(JSONObject node, List<SysMenu> sysMenuList) {
        List<JSONObject> nodeChildrenList = new ArrayList<>();
        Long nodeId = node.getLong(ConstantData.id);
        for (SysMenu sysMenu:sysMenuList){
            if (nodeId == sysMenu.getParentId()){
                JSONObject nodeChildren = new JSONObject();
                nodeChildren.putOpt(ConstantData.id,sysMenu.getId());
                nodeChildren.putOpt(ConstantData.menuName,sysMenu.getMenuName());
                getMenuChildren(nodeChildren,sysMenuList);
                nodeChildrenList.add(nodeChildren);
            }
        }
        node.putOpt(ConstantData.children,nodeChildrenList);
    }
}
