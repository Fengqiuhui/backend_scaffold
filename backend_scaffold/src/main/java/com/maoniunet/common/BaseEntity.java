package com.maoniunet.common;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class BaseEntity implements Serializable {

    @TableId
    protected Long id;

    /**
     * 时间格式统一采用 ISO 规范
     */
    @TableField("created_date")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    protected LocalDateTime createdDate;

    @TableField("last_modified_date")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    protected LocalDateTime lastModifiedDate;

    /**
     * 布尔类型字段
     * 数据库字段采用 is_xxx 格式, 应用代码里采用 xxx
     */
    @TableField("is_del")
    protected Boolean del;
}
