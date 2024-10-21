package com.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @Author：
 * @Package：com.dto
 * @Project：express
 * @name：LoginDto
 * @Date：2024/9/1 17:16
 * @Description:
 */
@Data
public class LoginDto {

    private String phone;

    private String code;

    private String username;

    private String password;

    /**
     * 登陆类型，1手机号或者2用户名密码
     */
    @NotNull(message = "登陆类型不能为空")
    private Integer loginType;
}
