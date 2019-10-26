package com.pjjlt.easyfile.moudle.service;

import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.pjjlt.easyfile.moudle.entity.ProjectFolder;
import com.pjjlt.easyfile.moudle.model.ProjectFolderModel;

import java.util.List;

public interface ProjectFolderService extends IService<ProjectFolder> {
    //新增文件夹
    void create(ProjectFolderModel projectInfoModel)throws Exception;
    //更新文件夹
    void updateProjectFolder(ProjectFolderModel projectInfoModel)throws Exception;
    //文件夹详情
    ProjectFolder info(ProjectFolderModel projectInfoModel)throws Exception;
    //文件夹删除
    void deleteProjectFolder(ProjectFolderModel projectInfoModel)throws Exception;
    //获取文件夹树状图
    List<JSONObject> tree(ProjectFolderModel projectInfoModel)throws Exception;
}
