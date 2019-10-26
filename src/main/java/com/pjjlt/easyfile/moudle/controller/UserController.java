package com.pjjlt.easyfile.moudle.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pjjlt.easyfile.enums.ResultEnum;
import com.pjjlt.easyfile.exception.DataException;
import com.pjjlt.easyfile.exception.DeleteException;
import com.pjjlt.easyfile.exception.PasswordException;
import com.pjjlt.easyfile.moudle.entity.SysUser;
import com.pjjlt.easyfile.moudle.model.SysUserModel;
import com.pjjlt.easyfile.moudle.service.SysUserService;
import com.pjjlt.easyfile.util.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

/**
 * 用户维护API
 *
 * @author pjjlt
 * */

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private SysUserService sysUserService;

    /**
     * 用户新增
     * */
//    @PreAuthorize("hasAnyAuthority('sys:user:create')")
    @PostMapping("create")
    public Result create(@RequestBody SysUserModel sysUserModel){
        // 判空
        if (sysUserModel.checkCreate()){
            return new Result(ResultEnum.PARAM_ERROR);
        }
        // 判断数据唯一性登录名、邮箱
        try {
            sysUserService.checkUnique(sysUserModel,false);
        }catch (Exception e){
            log.error("注册异常：{}", Arrays.toString(e.getStackTrace()));
            if (e instanceof DataException){
                return new Result(((DataException) e).getResultEnum(),e.getMessage());
            }
            return new Result(ResultEnum.ERROR,"新增用户失败");
        }
        sysUserService.create(sysUserModel);
        return new Result("新增用户成功!");
    }


    /**
     * 获取用户列表
     * */
//    @PreAuthorize("hasAnyAuthority('sys:user:list')")
    @PostMapping("list")
    public Result list(@RequestBody SysUserModel sysUserModel){
        // 判空
        if (sysUserModel.checkList()){
            return new Result(ResultEnum.PARAM_ERROR);
        }
        Page<SysUser> result = sysUserService.getPage(sysUserModel);
        return new Result(result);
    }

    /**
     * 获取登录的用户信息
     */
//    @PreAuthorize("hasAnyAuthority('sys:user:info')")
    @PostMapping("/info")
    public Result info(){
        SysUser sysUser = sysUserService.info();
        return new Result(sysUser);
    }

    /**
     * 获取用户信息
     */
//    @PreAuthorize("hasAnyAuthority('sys:user:getOne')")
    @PostMapping("/get-one")
    public Result getOne(@RequestBody SysUserModel sysUserModel){
        // 判空
        if (sysUserModel.checkGetOne()){
            return new Result(ResultEnum.PARAM_ERROR);
        }
        SysUser sysUser = sysUserService.getById(sysUserModel.getId());
        return new Result(sysUser);
    }


    /**
     * 更新用户信息
         * 只可以更改 user_name（昵称）、user_type（用户类型）、email（邮箱）、phone（手机号）、status（账号状态）、headImgUrl（头像）
     */
//    @PreAuthorize("hasAnyAuthority('sys:user:update')")
    @PostMapping("/update")
    public Result update(@RequestBody SysUserModel sysUserModel){
        // 判空
        if (sysUserModel.checkUpdate()){
            return new Result(ResultEnum.PARAM_ERROR);
        }
        try {
            // 判断数据唯一性登录名、邮箱
            sysUserService.checkUnique(sysUserModel,true);
            sysUserService.updateUser(sysUserModel);
            return new Result("更新用户成功!");
        }catch (Exception e){
            log.error("更新用户出错:{}", Arrays.toString(e.getStackTrace()));
            if (e instanceof DataException){
                return new Result(((DataException) e).getResultEnum(),e.getMessage());
            }
            return new Result(ResultEnum.ERROR,"更新用户出错!");
        }
    }

    /**
     * 修改登录用户密码
     */
//    @PreAuthorize("hasAnyAuthority('sys:user:password')")
    @PostMapping("/password")
    public Result password(@RequestBody SysUserModel sysUserModel){
        // 判空
        if (sysUserModel.checkPassword()){
            return new Result(ResultEnum.PARAM_ERROR);
        }
        if (!sysUserModel.getPassword().equals(sysUserModel.getPasswordAgain())){
            return new Result(ResultEnum.PASSWORD_ERROR,"两次密码不一致");
        }
        try {
            sysUserService.password(sysUserModel);
            return new Result("修改密码成功!");
        }catch (Exception e){
            log.error("修改密码出错:{}", Arrays.toString(e.getStackTrace()));
            if (e instanceof PasswordException){
                return new Result(((PasswordException) e).getResultEnum(),e.getMessage());
            }
            return new Result(ResultEnum.ERROR,"修改密码出错!");
        }
    }

    /**
     * 删除用户
     */
//    @PreAuthorize("hasAnyAuthority('sys:user:delete')")
    @PostMapping("/delete")
    public Result delete(@RequestBody SysUserModel sysUserModel){
        // 判空
        if (sysUserModel.checkGetOne()){
            return new Result(ResultEnum.PARAM_ERROR);
        }
        try {
            sysUserService.deleteUser(sysUserModel);
            return new Result("删除用户成功!");
        }catch (Exception e){
            log.error("删除用户出错:{}", Arrays.toString(e.getStackTrace()));
            if (e instanceof DeleteException){
                return new Result(((DeleteException) e).getResultEnum(),e.getMessage());
            }
            return new Result(ResultEnum.ERROR,"删除用户出错!");
        }
    }

}
