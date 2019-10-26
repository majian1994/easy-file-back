package com.pjjlt.easyfile.moudle.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pjjlt.easyfile.enums.ResultEnum;
import com.pjjlt.easyfile.moudle.entity.SysRole;
import com.pjjlt.easyfile.moudle.model.SysRoleModel;
import com.pjjlt.easyfile.moudle.service.SysRoleService;
import com.pjjlt.easyfile.util.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 角色维护
 * 只有超级管理员才能维护角色：新增、删除
 * 在项目中可以查看，给某用户赋值
 * @author pjjlt
 * */

@Slf4j
@RestController
@RequestMapping("/role")
public class RoleController {

    @Autowired
    SysRoleService sysRoleService;
    /**
     * 角色新增
     * */
//    @PreAuthorize("hasAnyAuthority('sys:role:create')")
    @PostMapping("create")
    public Result create(@RequestBody SysRoleModel sysRoleModel){
        // 判空
        if (sysRoleModel.checkCreate()){
            return new Result(ResultEnum.PARAM_ERROR);
        }
        sysRoleService.create(sysRoleModel);
        return new Result("新增角色成功!");
    }

    /**
     * 获取角色列表(分页)
     * */
//    @PreAuthorize("hasAnyAuthority('sys:role:list')")
    @PostMapping("list")
    public Result list(@RequestBody SysRoleModel sysRoleModel){
        // 如果不是超级管理员，需要传productId
        if (sysRoleModel.checkList()){
            return new Result(ResultEnum.PARAM_ERROR);
        }
        Page<SysRole> result = sysRoleService.getPage(sysRoleModel);
        return new Result(result);
    }

    /**
     * 获取单个角色
     * */
//    @PreAuthorize("hasAnyAuthority('sys:role:getOne')")
    @PostMapping("get-one")
    public Result getOne(@RequestBody SysRoleModel sysRoleModel){
        // 判空
        if (sysRoleModel.checkId()){
            return new Result(ResultEnum.PARAM_ERROR);
        }
        SysRole sysRole = sysRoleService.getById(sysRoleModel.getId());
        return new Result(sysRole);
    }

    /**
     * 更新角色
     * */
//    @PreAuthorize("hasAnyAuthority('sys:role:update')")
    @PostMapping("update")
    public Result update(@RequestBody SysRoleModel sysRoleModel){
        // 判空
        if (sysRoleModel.checkId()){
            return new Result(ResultEnum.PARAM_ERROR);
        }
        try {
            sysRoleService.updateRole(sysRoleModel);
        }catch (Exception e){
            log.error("更新角色失败：{}",e.getMessage());
            return new Result(ResultEnum.ERROR,"更新角色失败!");
        }
        return new Result("更新角色成功!");
    }

    /**
     * 删除角色
     * */
//    @PreAuthorize("hasAnyAuthority('sys:role:delete')")
    @PostMapping("delete")
    public Result delete(@RequestBody SysRoleModel sysRoleModel){
        // 判空
        if (sysRoleModel.checkId()){
            return new Result(ResultEnum.PARAM_ERROR);
        }
        try {
            sysRoleService.deleteRole(sysRoleModel);
            return new Result("删除角色成功!");
        }catch (Exception e){
            log.error("删除角色失败：{}",e.getMessage());
            return new Result(ResultEnum.ERROR,"删除角色失败!");
        }
    }

    /**
     * 给用户分配角色
     * */
    //    @PreAuthorize("hasAnyAuthority('sys:role:setRole')")
    @PostMapping("set-role")
    public Result setRole(@RequestBody SysRoleModel sysRoleModel){
        // 判空
        if (sysRoleModel.checkSetRole()){
            return new Result(ResultEnum.PARAM_ERROR);
        }
        try {
            sysRoleService.setRole(sysRoleModel);
            return new Result("分配角色成功!");
        }catch (Exception e){
            log.error("分配角色失败：{}",e.getMessage());
            return new Result(ResultEnum.ERROR,"分配角色失败!");
        }
    }

    /**
     * 给角色分配权限
     * */
    //    @PreAuthorize("hasAnyAuthority('sys:role:setMenu')")
    @PostMapping("set-menu")
    public Result setMenu(@RequestBody SysRoleModel sysRoleModel){
        // 判空
        if (sysRoleModel.checkSetMenu()){
            return new Result(ResultEnum.PARAM_ERROR);
        }
        try {
            sysRoleService.setMenu(sysRoleModel);
            return new Result("分配权限成功!");
        }catch (Exception e){
            log.error("分配权限失败：{}",e.getMessage());
            return new Result(ResultEnum.ERROR,"分配权限失败!");
        }
    }
}
