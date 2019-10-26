package com.pjjlt.easyfile.moudle.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.pjjlt.easyfile.moudle.entity.SysUser;
import com.pjjlt.easyfile.security.SecurityUserUtil;
import com.pjjlt.easyfile.util.VerifyUtil;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;


@Data
@Accessors(chain = true)
public class SysRoleModel extends BaseModel implements Serializable {
    private static final long serialVersionUID = 1L;
    /**角色ID*/
    private Long id;
    /**角色名称*/
    private String roleName;
    /**创建时间*/
    private LocalDateTime createTime;
    /**更新时间*/
    private LocalDateTime modifyTime;
    /**备注*/
    private String remark;
    /**用户Id**/
    private Long userId;
    /**项目Id*/
    private Long projectId;
    /**给role赋权限用的List*/
    private List<Long> menuList;

    /**
     * 新增角色判空
     * */
    public boolean checkCreate() {
        return VerifyUtil.hasNull(this.roleName);
    }
    /**
     * 判断id是否为空
     * */
    public boolean checkId() {
        return Objects.isNull(this.id);
    }
    /**
     * 如果不是超级管理员，需要传productId
     * */
    public boolean checkList() {
        return super.checkProductId(this.projectId);
    }
    /**
     * 给用户分配角色
     * */
    public boolean checkSetRole() {
        return VerifyUtil.hasNull(this.userId,this.id,this.projectId);
    }
    /**
     * 给角色分配权限
     * */
    public boolean checkSetMenu() {
        return VerifyUtil.hasNull(this.id,this.menuList);
    }
}
