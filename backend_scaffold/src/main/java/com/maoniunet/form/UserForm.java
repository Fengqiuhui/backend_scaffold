package com.maoniunet.form;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 * user 创建和更新用字段
 */
@Data
public class UserForm {

    @Length(min = 6, max = 16, message = "用户名应该在6到16个字符")
    private String username;
}
