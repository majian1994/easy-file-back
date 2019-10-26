package com.pjjlt.easyfile.security;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.pjjlt.easyfile.moudle.entity.SysMenu;
import com.pjjlt.easyfile.moudle.entity.SysUser;
import com.pjjlt.easyfile.moudle.model.SysMenuModel;
import com.pjjlt.easyfile.moudle.service.SysMenuService;
import com.pjjlt.easyfile.moudle.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;

/**
 * Security 要求需要实现的UserService类
 * */
@Service
public class SecurityUserService implements UserDetailsService{

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysMenuService sysMenuService;
    @Autowired
    private HttpServletRequest httpServletRequest;

    @Override
    public SecurityUser loadUserByUsername(String loginName) throws UsernameNotFoundException {
        LambdaQueryWrapper<SysUser> condition = Wrappers.<SysUser>lambdaQuery().eq(SysUser::getLoginName, loginName);
        SysUser sysUser = sysUserService.getOne(condition);
        if (Objects.isNull(sysUser)){
            throw new UsernameNotFoundException("未找到该用户!");
        }
        Long projectId = null;
        try{
            projectId = Long.parseLong(httpServletRequest.getHeader("projectId"));
        }catch (Exception e){

        }
        SysMenuModel sysMenuModel;
        if (sysUser.getUserType()){
            sysMenuModel = new SysMenuModel();
        }else {
            sysMenuModel = new SysMenuModel().setUserId(sysUser.getId());
        }
        sysMenuModel.setProjectId(projectId);
        List<SysMenu> menuList = sysMenuService.getList(sysMenuModel);
        return new SecurityUser(sysUser,menuList);
    }
}
