package com.pjjlt.easyfile.moudle.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>
 * 项目表
 * </p>
 *
 * @author pjjlt
 * @since 2019-10-16
 */
@Data
@Accessors(chain = true)
public class ProjectInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 项目id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 项目名称
     */
    private String projectName;

    /**
     * 创建者id
     */
    private Long userId;

    /**
     * 是否公开（0私有的 1公开的）
     */
    private Boolean publicFlag;

    /**
     * 项目描述
     */
    private String description;

    /**
     * 项目logo
     */
    private String projectLogo;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime modifyTime;

    /**
     * 备注
     */
    private String remark;


}
