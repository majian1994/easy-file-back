package com.pjjlt.easyfile.moudle.controller;

import cn.hutool.json.JSONObject;
import com.pjjlt.easyfile.enums.ResultEnum;
import com.pjjlt.easyfile.moudle.entity.ProjectFolder;
import com.pjjlt.easyfile.moudle.model.ProjectFolderModel;
import com.pjjlt.easyfile.moudle.service.ProjectFolderService;
import com.pjjlt.easyfile.util.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 文件夹API
 * @author pjjlt
 * */
@Slf4j
@RestController
@RequestMapping("/folder")
public class ProjectFolderController {

    @Autowired
    ProjectFolderService projectFolderService;

    /**
     * 文件夹新建
     * */
    //    @PreAuthorize("hasAnyAuthority('sys:folder:create')")
    @PostMapping("create")
    public Result create(@RequestBody ProjectFolderModel projectInfoModel) throws Exception{
        if (projectInfoModel.checkCreate()){
            return new Result(ResultEnum.PARAM_ERROR);
        }
        projectFolderService.create(projectInfoModel);
        return new Result("项目新建成功!");
    }

    /**
     * 文件夹详情
     * */
    //    @PreAuthorize("hasAnyAuthority('sys:folder:info')")
    @PostMapping("info")
    public Result info(@RequestBody ProjectFolderModel projectInfoModel) throws Exception{
        if (projectInfoModel.checkId()){
            return new Result(ResultEnum.PARAM_ERROR);
        }
        ProjectFolder result = projectFolderService.info(projectInfoModel);
        return new Result(result);
    }

    /**
     * 文件夹更新
     * */
    //    @PreAuthorize("hasAnyAuthority('sys:folder:update')")
    @PostMapping("update")
    public Result update(@RequestBody ProjectFolderModel projectInfoModel) throws Exception{
        if (projectInfoModel.checkUpdate()){
            return new Result(ResultEnum.PARAM_ERROR);
        }
        projectFolderService.updateProjectFolder(projectInfoModel);
        return new Result("项目更新成功!");
    }

    /**
     * 文件夹删除
     * 级联删除
     * */
    //    @PreAuthorize("hasAnyAuthority('sys:folder:delete')")
    @PostMapping("delete")
    public Result delete(@RequestBody ProjectFolderModel projectInfoModel) throws Exception{
        if (projectInfoModel.checkId()){
            return new Result(ResultEnum.PARAM_ERROR);
        }
        projectFolderService.deleteProjectFolder(projectInfoModel);
        return new Result("项目删除成功!");
    }

    /**
     * 获取文件夹树状
     * 目前做简单版的，文件夹不支持搜索，看所有
     * */
    //    @PreAuthorize("hasAnyAuthority('sys:folder:tree')")
    @PostMapping("tree")
    public Result tree(@RequestBody ProjectFolderModel projectInfoModel) throws Exception{
        if (projectInfoModel.checkProjectId()){
            return new Result(ResultEnum.PARAM_ERROR);
        }
        List<JSONObject> result = projectFolderService.tree(projectInfoModel);
        return new Result(result);
    }
}
