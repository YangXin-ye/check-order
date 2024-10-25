package com.service;

import com.dto.LoginDto;
import com.dto.RegisterDto;
import com.dto.UpdateUserDto;
import com.entity.AccountionRecord;
import com.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**

* @description 针对表【user】的数据库操作Service
* @createDate 2024-09-01 16:57:43
*/
public interface UserService extends IService<User> {

    /**
     * 注册用户
     * @param registerDto
     */
//    void register(RegisterDto registerDto);

    /**
     * 登录
     * @param loginDto
     * @return
     */
    String login(LoginDto loginDto);

    /**
     * 根据token获取用户
     * @return
     */
    User getLoginUser();


    void updateLoginUser(UpdateUserDto updateUserDto);


    String sendVerificationCode(String phone);

    void sendEmailCoe(String email);
}
