package com.pjjlt.easyfile.moudle.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>
 * 文档详情表
 * </p>
 *
 * @author pjjlt
 * @since 2019-10-16
 */
@Data
@Accessors(chain = true)
public class ProjectFileDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 文档详情id(同file的id)
     */
    @TableId(value = "id", type = IdType.INPUT)
    private Long id;

    /**
     * 文档详情
     */
    private String detail;

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
