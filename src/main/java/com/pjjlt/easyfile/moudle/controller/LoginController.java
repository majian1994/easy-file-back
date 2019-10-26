package com.pjjlt.easyfile.moudle.controller;

import cn.hutool.json.JSONObject;
import com.pjjlt.easyfile.enums.ResultEnum;
import com.pjjlt.easyfile.exception.DataException;
import com.pjjlt.easyfile.exception.LoginException;
import com.pjjlt.easyfile.moudle.model.SysUserModel;
import com.pjjlt.easyfile.moudle.service.SysUserService;
import com.pjjlt.easyfile.moudle.service.SysUserTokenService;
import com.pjjlt.easyfile.util.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

/**
 * 登录模块
 * @author pjjlt
 * */
@Slf4j
@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysUserTokenService sysUserTokenService;

    @PreAuthorize("hasAnyAuthority('test')")
    @GetMapping("test")
    public String test(){
        return "test";
    }

    /**
     * 注册接口
     * */
    @PostMapping("register")
    public Result register(@RequestBody SysUserModel sysUserModel){
        // 判空
        if (sysUserModel.checkCreate()){
            return new Result(ResultEnum.PARAM_ERROR);
        }
        // 判断数据唯一性登录名、邮箱、手机号
        try {
            sysUserService.checkUnique(sysUserModel,false);
        }catch (Exception e){
            log.error("注册异常：{}", Arrays.toString(e.getStackTrace()));
            if (e instanceof DataException){
                return new Result(((DataException) e).getResultEnum(),e.getMessage());
            }
            return new Result(ResultEnum.ERROR,e.getMessage());
        }
        sysUserService.create(sysUserModel);
        return new Result();
    }

    /**
     * 登录接口
     * */
    @PostMapping("login")
    public Result login(@RequestBody SysUserModel sysUserModel){
        if (sysUserModel.checkLogin()){
            return new Result(ResultEnum.PARAM_ERROR);
        }
        try {
            JSONObject result = sysUserService.login(sysUserModel);
            return new Result(result);
        }catch (Exception e){
            log.error("登录异常：{}", Arrays.toString(e.getStackTrace()));
            return new Result(ResultEnum.LOGIN_ERROR,e.getMessage());
        }
    }

    /**
     * 登出
     * */
    @PostMapping("logout")
    public Result logout(){
        try {
            sysUserTokenService.logout();
            return new Result();
        }catch (Exception e){
            log.error("登录异常：{}", Arrays.toString(e.getStackTrace()));
            if (e instanceof LoginException){
                return new Result(ResultEnum.LOGOUT_ERROR,e.getMessage());
            }
            return new Result(ResultEnum.ERROR,e.getMessage());
        }
    }

}
