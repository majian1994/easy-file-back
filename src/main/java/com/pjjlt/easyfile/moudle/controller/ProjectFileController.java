package com.pjjlt.easyfile.moudle.controller;

import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pjjlt.easyfile.enums.ResultEnum;
import com.pjjlt.easyfile.moudle.model.ProjectFileModel;
import com.pjjlt.easyfile.moudle.service.ProjectFileService;
import com.pjjlt.easyfile.util.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 文档API
 *
 * @author pjjlt
 * */
@Slf4j
@RestController
@RequestMapping("/file")
public class ProjectFileController {

    @Autowired
    ProjectFileService projectFileService;

    /**
     * 文档新建
     *  目前只支持markDown
     * */
    //    @PreAuthorize("hasAnyAuthority('sys:file:create')")
    @PostMapping("create")
    public Result create(@RequestBody ProjectFileModel projectFileModel) throws Exception{
        if (projectFileModel.checkCreate()){
            return new Result(ResultEnum.PARAM_ERROR);
        }
        projectFileService.create(projectFileModel);
        return new Result("新建文档成功!");
    }

    /**
     * 文档详情
     * */
    //    @PreAuthorize("hasAnyAuthority('sys:file:info')")
    @PostMapping("info")
    public Result info(@RequestBody ProjectFileModel projectFileModel) throws Exception{
        if (projectFileModel.checkId()){
            return new Result(ResultEnum.PARAM_ERROR);
        }
        JSONObject projectFile = projectFileService.info(projectFileModel);
        return new Result(projectFile);
    }

    /**
     * 更改文档
     * */
    //    @PreAuthorize("hasAnyAuthority('sys:file:update')")
    @PostMapping("update")
    public Result update(@RequestBody ProjectFileModel projectFileModel) throws Exception{
        if (projectFileModel.checkUpdate()){
            return new Result(ResultEnum.PARAM_ERROR);
        }
        projectFileService.updateFile(projectFileModel);
        return new Result("更新文档成功");
    }

    /**
     * 删除文档
     * */
    //    @PreAuthorize("hasAnyAuthority('sys:file:delete')")
    @PostMapping("delete")
    public Result delete(@RequestBody ProjectFileModel projectFileModel) throws Exception{
        if (projectFileModel.checkId()){
            return new Result(ResultEnum.PARAM_ERROR);
        }
        projectFileService.deleteFile(projectFileModel);
        return new Result("删除文档成功");
    }


    /**
     * 查看文档列表
     * */
    //    @PreAuthorize("hasAnyAuthority('sys:file:list')")
    @PostMapping("list")
    public Result list(@RequestBody ProjectFileModel projectFileModel) throws Exception{
        if (projectFileModel.checkList()){
            return new Result(ResultEnum.PARAM_ERROR);
        }
        Page<JSONObject> result = projectFileService.getPage(projectFileModel);
        return new Result(result);
    }

    /**
     * 将文档放入回收站
     * */
    //    @PreAuthorize("hasAnyAuthority('sys:file:recycle')")
    @PostMapping("recycle")
    public Result recycle(@RequestBody ProjectFileModel projectFileModel) throws Exception{
        if (projectFileModel.checkId()){
            return new Result(ResultEnum.PARAM_ERROR);
        }
        projectFileService.recycle(projectFileModel);
        return new Result("该文档已进入回收站!");
    }

    /**
     * 将草稿发布为正式文章
     * */
    //    @PreAuthorize("hasAnyAuthority('sys:file:publish')")
    @PostMapping("publish")
    public Result publish(@RequestBody ProjectFileModel projectFileModel) throws Exception{
        if (projectFileModel.checkId()){
            return new Result(ResultEnum.PARAM_ERROR);
        }
        projectFileService.publish(projectFileModel);
        return new Result("该草稿已发布!");
    }


    /**
     * 回收站还原
     * */
    //    @PreAuthorize("hasAnyAuthority('sys:file:restore')")
    @PostMapping("restore")
    public Result restore(@RequestBody ProjectFileModel projectFileModel) throws Exception{
        if (projectFileModel.checkId()){
            return new Result(ResultEnum.PARAM_ERROR);
        }
        projectFileService.restore(projectFileModel);
        return new Result("该文章已经还原!");
    }
}
