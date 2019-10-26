package com.pjjlt.easyfile.moudle.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.UUID;
import cn.hutool.extra.servlet.ServletUtil;
import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pjjlt.easyfile.constant.ConstantData;
import com.pjjlt.easyfile.enums.ResultEnum;
import com.pjjlt.easyfile.exception.DataException;
import com.pjjlt.easyfile.exception.DeleteException;
import com.pjjlt.easyfile.exception.PasswordException;
import com.pjjlt.easyfile.moudle.entity.ProjectInfo;
import com.pjjlt.easyfile.moudle.entity.SysUser;
import com.pjjlt.easyfile.moudle.entity.SysUserToken;
import com.pjjlt.easyfile.moudle.mapper.SysUserMapper;
import com.pjjlt.easyfile.moudle.model.SysUserModel;
import com.pjjlt.easyfile.moudle.service.*;
import com.pjjlt.easyfile.security.SecurityUserUtil;
import com.pjjlt.easyfile.util.CipherUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Autowired
    HttpServletRequest request;
    @Autowired
    SysUserTokenService sysUserTokenService;
    @Autowired
    ProjectInfoService projectInfoService;
    @Autowired
    SysUserRoleService sysUserRoleService;
    @Autowired
    SysUserProjectService sysUserProjectService;


    @Value("${easy.expire-time}")
    private Long expireTimeSeconds;

    /**
     * 新增用户
     * */
    @Override
    public void create(SysUserModel sysUserModel){
        //数据预处理
        sysUserModel.pre();
        SysUser sysUser = new SysUser();
        BeanUtil.copyProperties(sysUserModel,sysUser, ConstantData.id);
        baseMapper.insert(sysUser);
    }
    /**
     * 登录，返回登录信息，前端需要缓存
     * */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public JSONObject login(SysUserModel sysUserModel) throws Exception{
        JSONObject result = new JSONObject();
        //1. 验证账号是否存在、密码是否正确、账号是否停用
        Wrapper<SysUser> sysUserWrapper = Wrappers.<SysUser>lambdaQuery()
                .eq(SysUser::getLoginName,sysUserModel.getLoginName()).or()
                .eq(SysUser::getEmail,sysUserModel.getEmail());
        SysUser sysUser = baseMapper.selectOne(sysUserWrapper);
        if (Objects.isNull(sysUser)){
            throw new Exception("用户不存在!");
        }
        String password = CipherUtil.encryptByAES(sysUserModel.getPassword());
        if (!password.equals(sysUser.getPassword())){
            throw new Exception("密码不正确!");
        }
        if (sysUser.getStatus()){
            throw new Exception("账号已删除或已停用!");
        }
        // 2.更新最后登录时间
        sysUser.setLoginIp(ServletUtil.getClientIP(request));
        sysUser.setLoginDate(LocalDateTime.now());
        baseMapper.updateById(sysUser);
        // 3.封装token，返回信息
        String token = UUID.fastUUID().toString().replace("-","");
        LocalDateTime expireTime = LocalDateTime.now().plusSeconds(expireTimeSeconds);
        SysUserToken sysUserToken = new SysUserToken()
                .setToken(token).setUserId(sysUser.getId()).setExpireTime(expireTime);
        sysUserTokenService.save(sysUserToken);
        result.putOpt("token",token);
        result.putOpt("expireTime",expireTime);
        return result;
    }
    /**
     * 判断数据唯一性用户名、邮箱、手机号
     * */
    @Override
    public void checkUnique(SysUserModel sysUserModel,boolean isUpdate) throws Exception{
        LambdaQueryWrapper<SysUser> loginNameWrapper = Wrappers.<SysUser>lambdaQuery()
                .eq(SysUser::getLoginName,sysUserModel.getLoginName());
        if (isUpdate){
            loginNameWrapper.ne(SysUser::getId,sysUserModel.getId());
        }
        List<SysUser> loginNameResult = baseMapper.selectList(loginNameWrapper);
        if (loginNameResult.size()>0){
            throw new DataException("登录名重复",ResultEnum.DATA_REPEAT);
        }
        LambdaQueryWrapper<SysUser> emailWrapper = Wrappers.<SysUser>lambdaQuery()
                .eq(SysUser::getEmail,sysUserModel.getEmail());
        if (isUpdate){
            emailWrapper.ne(SysUser::getId,sysUserModel.getId());
        }
        List<SysUser> emailResult = baseMapper.selectList(emailWrapper);
        if (emailResult.size()>0){
            throw new DataException("email重复",ResultEnum.DATA_REPEAT);
        }
        if (Objects.nonNull(sysUserModel.getPhone())){
            LambdaQueryWrapper<SysUser> phoneWrapper = Wrappers.<SysUser>lambdaQuery()
                    .eq(SysUser::getPhone,sysUserModel.getPhone());
            if (isUpdate){
                phoneWrapper.ne(SysUser::getId,sysUserModel.getId());
            }
            List<SysUser> phoneResult = baseMapper.selectList(phoneWrapper);
            if (phoneResult.size()>0){
                throw new DataException("手机号重复",ResultEnum.DATA_REPEAT);
            }
        }
    }
    /**
     * 分页获取用户
     * */
    @Override
    public Page<SysUser> getPage(SysUserModel sysUserModel) {
        Page<SysUser> page = sysUserModel.toPage();
        Page<SysUser> result = baseMapper.getPage(page,sysUserModel);
        return result;
    }
    /**
     * 获取当前用户
     * */
    @Override
    public SysUser info() {
        SysUser sysUser = SecurityUserUtil.getCurrentUser();
        if (Objects.isNull(sysUser)){
            sysUser = new SysUser();
        }
        return sysUser;
    }
    /**
     * 更新用户信息
     * 只可以更改 user_name（昵称）、user_type（用户类型）、email（邮箱）、phone（手机号）、status（账号状态）
     */
    @Override
    public void updateUser(SysUserModel sysUserModel) throws Exception {
        SysUser sysUser = baseMapper.selectById(sysUserModel.getId());
        if (Objects.isNull(sysUser)){
            throw new Exception("用户不存在!");
        }
        sysUser.setUserName(sysUserModel.getUserName()).setUserType(sysUserModel.getUserType())
                .setEmail(sysUserModel.getEmail()).setPhone(sysUserModel.getPhone())
                .setStatus(sysUserModel.getStatus()).setModifyTime(LocalDateTime.now());
        baseMapper.updateById(sysUser);
    }
    /**
     * 修改密码
     * */
    @Override
    public void password(SysUserModel sysUserModel) throws Exception{
        SysUser sysUser = baseMapper.selectById(sysUserModel.getId());
        if (Objects.isNull(sysUser)){
            throw new Exception("用户不存在!");
        }
        sysUser.setPassword( CipherUtil.encryptByAES(sysUserModel.getPassword()));
        baseMapper.updateById(sysUser);
    }
    /**
     * 删除用户
     * 1.如果是超级管理员，不能删除
     * 2.如果是还有创建的项目，不能删除
     * 3.级联删除 sys_user_project、sys_user_role、sys_user_token
     * */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteUser(SysUserModel sysUserModel) throws Exception{
        SysUser sysUser = baseMapper.selectById(sysUserModel.getId());
        if (Objects.isNull(sysUser)){
            throw new Exception("用户不存在!");
        }
        if (sysUser.getUserType()==true){
            throw new DeleteException("超级管理员无法删除!",ResultEnum.DELETE_ERROR);
        }
        LambdaQueryWrapper<ProjectInfo> lambdaQueryWrapper = Wrappers.<ProjectInfo>lambdaQuery()
                .eq(ProjectInfo::getUserId,sysUserModel.getId());
        List<ProjectInfo> projectInfoList = projectInfoService.list(lambdaQueryWrapper);
        if (projectInfoList.size()>0){
            throw new DeleteException("该用户拥有主创项目，无法删除!",ResultEnum.DELETE_ERROR);
        }
        Wrapper wrapper = Wrappers.query()
                .eq("user_id",sysUser.getId());
        sysUserProjectService.remove(wrapper);
        sysUserRoleService.remove(wrapper);
        sysUserTokenService.remove(wrapper);
        baseMapper.deleteById(sysUser);
    }

    public static void main(String[] args) {
        System.out.println(UUID.fastUUID().toString().replace("-",""));
    }
}
