package com.maoniunet.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.maoniunet.dto.UserDTO;
import com.maoniunet.entity.UserEntity;

public interface UserService extends IService<UserEntity> {

    Page<UserDTO> listUsers(Page<UserEntity> page);
}
