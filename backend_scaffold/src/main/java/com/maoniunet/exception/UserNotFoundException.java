package com.maoniunet.exception;

/**
 * Created by lmw on 2017/4/28.
 */
public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException() {
        super("用户不存在");
    }
}
