package com.pjjlt.easyfile.moudle.model;

import com.pjjlt.easyfile.util.VerifyUtil;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@Data
@Accessors(chain = true)
public class SysMenuModel extends BaseModel implements Serializable {

    private static final long serialVersionUID = 1L;

    /**菜单ID*/
    private Long id;

    /**菜单名称*/
    private String menuName;

    /** 父菜单ID*/
    private Long parentId;

    /**显示顺序*/
    private Integer orderNum;

    /**请求地址*/
    private String url;

    /**菜单类型（M目录 C菜单 F按钮）*/
    private String menuType;

    /**菜单状态（0显示 1隐藏）*/
    private Boolean visible;

    /**权限标识*/
    private String perms;

    /**菜单图标*/
    private String icon;

    /**创建时间*/
    private LocalDateTime createTime;

    /**更新时间*/
    private LocalDateTime modifyTime;

    /**备注*/
    private String remark;

    /**用户Id*/
    private Long userId;

    /**项目Id*/
    private Long projectId;

    /**
     * 新建菜单时验证参数
     * */
    public boolean checkCreate() {
        boolean check = VerifyUtil.hasNull(this.menuName,this.perms);
        return check;
    }
    /**
     * 更新菜单时验证参数
     * */
    public boolean updateCreate() {
        if (checkCreate()){
            return true;
        }
        return Objects.isNull(this.id);
    }
    /**
     * 检查id是否为空
     * */
    public boolean checkId() {
        return Objects.isNull(this.id);
    }
    /**
     * 数据预处理
     * */
    public void pre() {
        if (Objects.isNull(this.parentId)){
            this.setProjectId(0L);
        }
        if (Objects.isNull(this.orderNum)){
            this.setOrderNum(0);
        }
    }
}
