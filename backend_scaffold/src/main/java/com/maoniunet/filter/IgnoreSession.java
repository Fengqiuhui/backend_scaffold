package com.maoniunet.filter;

import java.lang.annotation.*;

/**
 * 标记请求忽略 session 验证
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(value = RetentionPolicy.RUNTIME)
@Inherited
public @interface IgnoreSession {
}
