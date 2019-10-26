package com.pjjlt.easyfile.moudle.model;

import com.pjjlt.easyfile.util.VerifyUtil;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@Data
@Accessors(chain = true)
public class ProjectInfoModel extends BaseModel implements Serializable {
    private static final long serialVersionUID = 1L;
    /**项目id*/
    private Long id;
    /**项目名称*/
    private String projectName;
    /**创建者id*/
    private Long userId;
    /**是否公开（0私有的 1公开的）*/
    private Boolean publicFlag;
    /**项目描述*/
    private String description;
    /**项目logo*/
    private String projectLogo;
    /**创建时间*/
    private LocalDateTime createTime;
    /**更新时间*/
    private LocalDateTime modifyTime;
    /**备注*/
    private String remark;
    /**搜索关键词*/
    private String keyword;
    /**当前登录者Id*/
    private Long currentUserId;

    /**
     * 新建验证
     * */
    public boolean checkCreate() {
        return VerifyUtil.hasNull(this.projectName);
    }
    /**
     * 列表验证
     * */
    public boolean checkLIst() {
        return false;
    }
    /**
     * 验证id是否存在
     * */
    public boolean checkId() {
        return Objects.isNull(this.id);
    }
    /**
     * 更新时验参
     * */
    public boolean checkUpdate() {
        boolean check = checkCreate();
        if (check){
            return true;
        }
        return Objects.isNull(this.id);
    }
}
