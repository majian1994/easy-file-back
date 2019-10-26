package com.pjjlt.easyfile.moudle.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pjjlt.easyfile.constant.ConstantData;
import com.pjjlt.easyfile.enums.FileEnum;
import com.pjjlt.easyfile.moudle.entity.*;
import com.pjjlt.easyfile.moudle.mapper.ProjectInfoMapper;
import com.pjjlt.easyfile.moudle.model.ProjectInfoModel;
import com.pjjlt.easyfile.moudle.service.ProjectFileService;
import com.pjjlt.easyfile.moudle.service.ProjectFolderService;
import com.pjjlt.easyfile.moudle.service.ProjectInfoService;
import com.pjjlt.easyfile.moudle.service.SysUserProjectService;
import com.pjjlt.easyfile.security.SecurityUserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Objects;

@Service
public class ProjectInfoServiceImpl extends ServiceImpl<ProjectInfoMapper, ProjectInfo> implements ProjectInfoService {

    @Autowired
    SysUserProjectService sysUserProjectService;
    @Autowired
    ProjectFolderService projectFolderService;
    @Autowired
    ProjectFileService projectFileService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(ProjectInfoModel projectInfoModel) throws Exception {
        SysUser sysUser = SecurityUserUtil.getCurrentUser();
        if (Objects.isNull(sysUser)){
            throw new Exception("当前登陆人不存在!");
        }
        ProjectInfo projectInfo = new ProjectInfo();
        BeanUtil.copyProperties(projectInfoModel,projectInfo, ConstantData.id);
        projectInfo.setUserId(sysUser.getId());
        baseMapper.insert(projectInfo);
        //新增用户项目关系表
        SysUserProject sysUserProject = new SysUserProject()
                .setProjectId(projectInfo.getId()).setUserId(sysUser.getId());
        sysUserProjectService.save(sysUserProject);
    }

    @Override
    public Page<ProjectInfo> getPage(ProjectInfoModel projectInfoModel) throws Exception {
        SysUser sysUser = SecurityUserUtil.getCurrentUser();
        if (Objects.isNull(sysUser)){
            throw new Exception("当前登陆人不存在!");
        }
        if (!sysUser.getUserType()){
            projectInfoModel.setCurrentUserId(sysUser.getId());
        }
        Page<ProjectInfo> page = projectInfoModel.toPage();
        Page<ProjectInfo> result = baseMapper.getPage(page,projectInfoModel);
        return result;
    }

    @Override
    public ProjectInfo getInfo(ProjectInfoModel projectInfoModel) throws Exception{
        ProjectInfo projectInfo = baseMapper.selectById(projectInfoModel.getId());
        return projectInfo;
    }

    @Override
    public void updateProjectInfo(ProjectInfoModel projectInfoModel) throws Exception {
        SysUser sysUser = SecurityUserUtil.getCurrentUser();
        if (Objects.isNull(sysUser)){
            throw new Exception("当前登陆人不存在!");
        }
        ProjectInfo projectInfo = baseMapper.selectById(projectInfoModel.getId());
        if (Objects.isNull(projectInfo)){
            throw new Exception("项目不存在!");
        }
        if (!sysUser.getUserType() && projectInfo.getUserId() != sysUser.getId()){
            throw new Exception("你无权更新这个项目信息!");
        }
        projectInfo.setDescription(projectInfoModel.getDescription()).setProjectName(projectInfo.getProjectName())
                .setRemark(projectInfoModel.getRemark()).setProjectLogo(projectInfoModel.getProjectLogo())
                .setPublicFlag(projectInfoModel.getPublicFlag())
                .setModifyTime(LocalDateTime.now());
        baseMapper.updateById(projectInfo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteProjectInfo(ProjectInfoModel projectInfoModel) throws Exception {
        SysUser sysUser = SecurityUserUtil.getCurrentUser();
        if (Objects.isNull(sysUser)){
            throw new Exception("当前登陆人不存在!");
        }
        ProjectInfo projectInfo = baseMapper.selectById(projectInfoModel.getId());
        if (Objects.isNull(projectInfo)){
            throw new Exception("项目不存在!");
        }
        if (!sysUser.getUserType() && projectInfo.getUserId() != sysUser.getId()){
            throw new Exception("你无权删除这个项目信息!");
        }
        //级联删除 文件夹、文件送进回收站、删除sys_user_project、删除项目
        Wrapper<ProjectFolder> projectFolderWrapper = Wrappers.<ProjectFolder>lambdaQuery()
                .eq(ProjectFolder::getProjectId,projectInfo.getId());
        projectFolderService.remove(projectFolderWrapper);
        ProjectFile setProjectFile = new ProjectFile().setFileStatus(FileEnum.RECYCLE);
        LambdaUpdateWrapper<ProjectFile> projectFileWrapper = Wrappers.<ProjectFile>lambdaUpdate()
                .eq(ProjectFile::getProjectId, projectInfo.getId());
        projectFileService.update(setProjectFile,projectFileWrapper);
        Wrapper<SysUserProject> sysUserProjectWrapper = Wrappers.<SysUserProject>lambdaQuery()
                .eq(SysUserProject::getProjectId,projectInfo.getId());
        sysUserProjectService.remove(sysUserProjectWrapper);
        baseMapper.deleteById(projectInfo);
    }
}
