package com.controller;

import com.dto.LoginDto;
import com.dto.RegisterDto;
import com.dto.UpdateUserDto;
import com.entity.User;
import com.service.UserService;
import com.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * @Author：
 * @Package：com.controller
 * @Project：express
 * @name：UserController
 * @Date：2024/9/1 16:59
 * @Description:
 */
@RestController
@RequestMapping("/user")
public class UserController {


    @Autowired
    private UserService userService;


    /**
     * 登陆接口
     * @param loginDto
     * @return
     */
    @PostMapping("/login")
    public R login(@RequestBody @Validated LoginDto loginDto){
        String token = userService.login(loginDto);
        return R.ok().put("token",token);
    }

    /**
     * 获取验证码
     * @param phone
     * @return
     */
    @GetMapping("/sendVerificationCode")
    public R sendVerificationCode(@RequestParam("phone") String phone){
        userService.sendVerificationCode(phone);
        return R.ok();
    }

    /**
     * 获取当前登陆用户接口
     * @return
     */
    @GetMapping("/getLoginUser")
    public R getLoginUser(){
        User user = userService.getLoginUser();
        return R.ok().put("user",user);
    }

    /**
     * 更新当前登陆用户信息
     * @param updateUserDto
     * @return
     */
    @PostMapping("/updateLoginUser")
    public R updateLoginUser(@RequestBody UpdateUserDto updateUserDto){
        userService.updateLoginUser(updateUserDto);
        return R.ok();
    }

}
