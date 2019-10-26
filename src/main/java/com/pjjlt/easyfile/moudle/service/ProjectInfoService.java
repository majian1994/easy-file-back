package com.pjjlt.easyfile.moudle.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.pjjlt.easyfile.moudle.entity.ProjectInfo;
import com.pjjlt.easyfile.moudle.model.ProjectInfoModel;

public interface ProjectInfoService extends IService<ProjectInfo> {
    //项目新建
    void create(ProjectInfoModel projectInfoModel) throws Exception;
    //项目列表
    Page<ProjectInfo> getPage(ProjectInfoModel projectInfoModel) throws Exception;
    //查看项目详情
    ProjectInfo getInfo(ProjectInfoModel projectInfoModel)throws Exception;
    //项目更新
    void updateProjectInfo(ProjectInfoModel projectInfoModel)throws Exception;
    //删除项目
    void deleteProjectInfo(ProjectInfoModel projectInfoModel)throws Exception;
}
