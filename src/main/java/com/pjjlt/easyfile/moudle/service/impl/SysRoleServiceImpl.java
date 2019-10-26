package com.pjjlt.easyfile.moudle.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pjjlt.easyfile.constant.ConstantData;
import com.pjjlt.easyfile.moudle.entity.SysRole;
import com.pjjlt.easyfile.moudle.entity.SysRoleMenu;
import com.pjjlt.easyfile.moudle.entity.SysUser;
import com.pjjlt.easyfile.moudle.entity.SysUserRole;
import com.pjjlt.easyfile.moudle.mapper.SysRoleMapper;
import com.pjjlt.easyfile.moudle.model.SysRoleModel;
import com.pjjlt.easyfile.moudle.service.SysRoleMenuService;
import com.pjjlt.easyfile.moudle.service.SysRoleService;
import com.pjjlt.easyfile.moudle.service.SysUserRoleService;
import com.pjjlt.easyfile.moudle.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {

    @Autowired
    SysUserRoleService sysUserRoleService;
    @Autowired
    SysUserService sysUserService;
    @Autowired
    SysRoleMenuService sysRoleMenuService;

    @Override
    public List<SysRole> getList(SysRoleModel sysRoleModel) {
        return baseMapper.getList(sysRoleModel);
    }

    @Override
    public void create(SysRoleModel sysRoleModel) {
        SysRole sysRole = new SysRole();
        BeanUtil.copyProperties(sysRoleModel,sysRole, ConstantData.id);
        baseMapper.insert(sysRole);
    }

    @Override
    public Page<SysRole> getPage(SysRoleModel sysRoleModel) {
        Page<SysRole> page = sysRoleModel.toPage();
        Page<SysRole> result = baseMapper.getPage(page,sysRoleModel);
        return result;
    }

    @Override
    public void updateRole(SysRoleModel sysRoleModel) throws Exception{
        SysRole sysRole = baseMapper.selectById(sysRoleModel.getId());
        if (Objects.isNull(sysRole)){
            throw new Exception("该角色不存在!");
        }
        sysRole.setRoleName(sysRoleModel.getRoleName())
                .setRemark(sysRoleModel.getRemark()).setModifyTime(LocalDateTime.now());
        baseMapper.updateById(sysRole);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteRole(SysRoleModel sysRoleModel) throws Exception{
        SysRole sysRole = baseMapper.selectById(sysRoleModel.getId());
        if (Objects.isNull(sysRole)){
            throw new Exception("该角色不存在!");
        }
        //级联删除 sys_user_role
        Wrapper<SysUserRole> wrapper = Wrappers.<SysUserRole>lambdaQuery()
                .eq(SysUserRole::getRoleId,sysRole.getId());
        sysUserRoleService.remove(wrapper);
        baseMapper.deleteById(sysRole);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void setRole(SysRoleModel sysRoleModel) throws Exception{
        SysUser sysUser = sysUserService.getById(sysRoleModel.getUserId());
        if (Objects.isNull(sysUser)){
            throw new Exception("该用户不存在!");
        }
        SysRole sysRole = baseMapper.selectById(sysRoleModel.getId());
        if (Objects.isNull(sysRole)){
            throw new Exception("该角色不存在!");
        }
        SysUserRole sysUserRole = new SysUserRole()
                .setRoleId(sysRole.getId()).setUserId(sysUser.getId()).setProjectId(sysRoleModel.getProjectId());
        Wrapper<SysUserRole> wrapper = Wrappers.<SysUserRole>lambdaQuery()
                .eq(SysUserRole::getRoleId,sysRole.getId()).eq(SysUserRole::getProjectId,sysRoleModel.getProjectId());
        sysUserRoleService.remove(wrapper);
        sysUserRoleService.save(sysUserRole);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void setMenu(SysRoleModel sysRoleModel) throws Exception{
        SysRole sysRole = baseMapper.selectById(sysRoleModel.getId());
        if (Objects.isNull(sysRole)){
            throw new Exception("该角色不存在!");
        }
        Wrapper<SysRoleMenu> wrapper = Wrappers.<SysRoleMenu>lambdaQuery()
                .eq(SysRoleMenu::getRoleId,sysRole.getId());
        sysRoleMenuService.remove(wrapper);
        List<SysRoleMenu> sysRoleMenuList = new ArrayList<>();
        for (Long menuId:sysRoleModel.getMenuList()){
            SysRoleMenu sysRoleMenu = new SysRoleMenu();
            sysRoleMenu.setMenuId(menuId);
            sysRoleMenu.setRoleId(sysRole.getId());
            sysRoleMenuList.add(sysRoleMenu);
        }
        sysRoleMenuService.saveBatch(sysRoleMenuList);
    }
}
