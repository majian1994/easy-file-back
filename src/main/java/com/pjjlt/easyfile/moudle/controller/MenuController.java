package com.pjjlt.easyfile.moudle.controller;

import cn.hutool.json.JSONObject;
import com.pjjlt.easyfile.enums.ResultEnum;
import com.pjjlt.easyfile.moudle.model.SysMenuModel;
import com.pjjlt.easyfile.moudle.service.SysMenuService;
import com.pjjlt.easyfile.util.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 菜单权限API
 *
 * @author pjjlt
 * */
@Slf4j
@RestController
@RequestMapping("/menu")
public class MenuController {
    @Autowired
    SysMenuService sysMenuService;

    /**
     * 菜单新增
     * */
//    @PreAuthorize("hasAnyAuthority('sys:menu:create')")
    @PostMapping("create")
    public Result create(@RequestBody SysMenuModel sysMenuModel){
        // 判空
        if (sysMenuModel.checkCreate()){
            return new Result(ResultEnum.PARAM_ERROR);
        }
        sysMenuService.create(sysMenuModel);
        return new Result("新增菜单成功!");
    }

    /**
     * 菜单更新
     * */
//    @PreAuthorize("hasAnyAuthority('sys:menu:update')")
    @PostMapping("update")
    public Result update(@RequestBody SysMenuModel sysMenuModel){
        // 判空
        if (sysMenuModel.updateCreate()){
            return new Result(ResultEnum.PARAM_ERROR);
        }
        try {
            sysMenuService.updateMenu(sysMenuModel);
            return new Result("更新菜单成功!");
        }catch (Exception e){
            log.error("更新菜单失败：{}",e.getMessage());
            return new Result(ResultEnum.ERROR,"更新菜单失败!");
        }
    }

    /**
     * 删除菜单
     * */
//    @PreAuthorize("hasAnyAuthority('sys:menu:delete')")
    @PostMapping("delete")
    public Result delete(@RequestBody SysMenuModel sysMenuModel){
        // 判空
        if (sysMenuModel.checkId()){
            return new Result(ResultEnum.PARAM_ERROR);
        }
        try {
            sysMenuService.deleteMenu(sysMenuModel);
            return new Result("删除菜单成功!");
        }catch (Exception e){
            log.error("删除菜单失败：{}",e.getMessage());
            return new Result(ResultEnum.ERROR,"删除菜单失败!");
        }
    }

    /**
     * 获取权限树
     * 仅支持like查询菜单名称
     * */
    //    @PreAuthorize("hasAnyAuthority('sys:menu:tree')")
    @PostMapping("tree")
    public Result tree(@RequestBody SysMenuModel sysMenuModel){
        try {
            List<JSONObject> tree = sysMenuService.tree(sysMenuModel);
            return new Result(tree);
        }catch (Exception e){
            log.error("获取权限树失败：{}",e.getMessage());
            return new Result(ResultEnum.ERROR,"获取权限树失败!");
        }
    }
}
