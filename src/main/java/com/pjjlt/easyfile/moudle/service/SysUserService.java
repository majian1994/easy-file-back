package com.pjjlt.easyfile.moudle.service;

import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.pjjlt.easyfile.moudle.entity.SysUser;
import com.pjjlt.easyfile.moudle.model.SysUserModel;

public interface SysUserService extends IService<SysUser> {
    JSONObject login(SysUserModel sysUserModel) throws Exception;
    //新增用户
    void create(SysUserModel sysUserModel);
    //分页获取用户列表
    Page<SysUser> getPage(SysUserModel sysUserModel);
    //获取当前用户
    SysUser info();
    //更新用户信息
    void updateUser(SysUserModel sysUserModel) throws Exception;
    //修改密码
    void password(SysUserModel sysUserModel) throws Exception;
    //删除用户
    void deleteUser(SysUserModel sysUserModel) throws Exception;
    //判断数据是否重复
    void checkUnique(SysUserModel sysUserModel,boolean isUpdate) throws Exception;
}
