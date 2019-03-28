package com.maoniunet.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.maoniunet.common.BaseEntity;
import lombok.Data;

@Data
@TableName("user")
public class UserEntity extends BaseEntity {

    @TableField("username")
    private String username;
}
