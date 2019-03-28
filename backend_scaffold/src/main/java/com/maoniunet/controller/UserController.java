package com.maoniunet.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.maoniunet.common.BaseController;
import com.maoniunet.dto.UserDTO;
import com.maoniunet.entity.UserEntity;
import com.maoniunet.exception.UserNotFoundException;
import com.maoniunet.form.UserForm;
import com.maoniunet.result.Result;
import com.maoniunet.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * rest api
 * /api/v1 api版本
 * /users 资源使用复数
 */
@RestController
@RequestMapping(value = "/api/v1/users", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class UserController extends BaseController {

    @Resource
    private UserService userService;

    @GetMapping
    public ResponseEntity<Result> listUsers(Page<UserEntity> page) {
        Page<UserDTO> dtoPage = userService.listUsers(page);
        return Result.success(dtoPage);
    }

    @GetMapping("/{id}")
    public void getUser(@PathVariable Long id) {
        throw new UserNotFoundException();
    }

    /**
     * 数据检验
     * 不带 {@link BindingResult}, 默认 {@link ResponseEntityExceptionHandler} 处理
     * @param userForm
     */
    @PostMapping
    public ResponseEntity<Result> createUser(@Valid @RequestBody UserForm userForm) {
        return Result.success(userForm, HttpStatus.CREATED);
    }

    /**
     * {@link Valid} 后面带 {@link BindingResult}
     * @param id
     * @param userForm
     * @param bindingResult
     * @return
     */
    @PutMapping("/{id}")
    public ResponseEntity<Result> updateUser(@PathVariable Long id,
                                             @Valid @RequestBody UserForm userForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return Result.error(bindingResult.toString(), HttpStatus.BAD_REQUEST);
        }
        return Result.success(userForm, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Result> deleteUser(@PathVariable Long id) {
        return Result.success(HttpStatus.NO_CONTENT);
    }

    /**
     * 把动作转换成资源
     * 比如修改 del 字段
     */

    @PutMapping("/{id}/del")
    public void updateUserDel(@PathVariable Long id) {

    }

    @DeleteMapping("/{id}/del")
    public void deleteUserDel(@PathVariable Long id) {

    }
}
