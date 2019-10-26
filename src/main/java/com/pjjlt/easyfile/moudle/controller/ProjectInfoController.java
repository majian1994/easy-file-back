package com.pjjlt.easyfile.moudle.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pjjlt.easyfile.enums.ResultEnum;
import com.pjjlt.easyfile.moudle.entity.ProjectInfo;
import com.pjjlt.easyfile.moudle.entity.SysUser;
import com.pjjlt.easyfile.moudle.model.ProjectInfoModel;
import com.pjjlt.easyfile.moudle.service.ProjectInfoService;
import com.pjjlt.easyfile.util.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

/**
 * 项目API
 * @author pjjlt
 * */
@Slf4j
@RestController
@RequestMapping("/project")
public class ProjectInfoController {
    @Autowired
    ProjectInfoService projectInfoService;

    /**
     * 项目新建
     * */
    //    @PreAuthorize("hasAnyAuthority('sys:project:create')")
    @PostMapping("create")
    public Result create(@RequestBody ProjectInfoModel projectInfoModel) throws Exception{
        if (projectInfoModel.checkCreate()){
            return new Result(ResultEnum.PARAM_ERROR);
        }
        projectInfoService.create(projectInfoModel);
        return new Result("项目新建成功!");
    }

    /**
     * 项目列表
     * 超级管理员看所有
     * 普通用户只能看自己加入的
     * */
    //    @PreAuthorize("hasAnyAuthority('sys:project:list')")
    @PostMapping("list")
    public Result list(@RequestBody ProjectInfoModel projectInfoModel) throws Exception{
        if (projectInfoModel.checkLIst()){
            return new Result(ResultEnum.PARAM_ERROR);
        }
        Page<ProjectInfo> result = projectInfoService.getPage(projectInfoModel);
        return new Result(result);
    }

    /**
     * 项目详情
     * */
    //    @PreAuthorize("hasAnyAuthority('sys:project:info')")
    @PostMapping("info")
    public Result info(@RequestBody ProjectInfoModel projectInfoModel) throws Exception{
        if (projectInfoModel.checkId()){
            return new Result(ResultEnum.PARAM_ERROR);
        }
        ProjectInfo result = projectInfoService.getInfo(projectInfoModel);
        return new Result(result);
    }

    /**
     * 项目更新
     * 只有超级管理员和项目的创建者可以更新项目信息
     * */
    //    @PreAuthorize("hasAnyAuthority('sys:project:update')")
    @PostMapping("update")
    public Result update(@RequestBody ProjectInfoModel projectInfoModel) throws Exception{
        if (projectInfoModel.checkUpdate()){
            return new Result(ResultEnum.PARAM_ERROR);
        }
        projectInfoService.updateProjectInfo(projectInfoModel);
        return new Result("项目更新成功!");
    }

    /**
     * 项目删除
     * */
    //    @PreAuthorize("hasAnyAuthority('sys:project:delete')")
    @PostMapping("delete")
    public Result delete(@RequestBody ProjectInfoModel projectInfoModel) throws Exception{
        if (projectInfoModel.checkId()){
            return new Result(ResultEnum.PARAM_ERROR);
        }
        projectInfoService.deleteProjectInfo(projectInfoModel);
        return new Result("项目删除成功!");
    }

}
