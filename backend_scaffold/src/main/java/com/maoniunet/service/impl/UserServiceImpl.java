package com.maoniunet.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.maoniunet.dto.UserDTO;
import com.maoniunet.entity.UserEntity;
import com.maoniunet.mapper.UserMapper;
import com.maoniunet.service.UserService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.ClassUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, UserEntity> implements UserService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private ModelMapper modelMapper;

    @Override
    public Page<UserDTO> listUsers(Page<UserEntity> page) {
        Page<UserEntity> userEntityPage = this.selectPage(page);
        List<UserEntity> records = userEntityPage.getRecords();
        List<UserDTO> dtoList = modelMapper.map(records, new TypeToken<List<UserDTO>>() {}.getType());
        Page<UserDTO> data = new Page<>(page.getCurrent(), page.getSize());
        data.setRecords(dtoList);
        data.setTotal(userEntityPage.getTotal());
        return data;
    }
}
