package com.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

/**
 * @Author：
 * @Package：com.dto
 * @Project：express
 * @name：RegisterDto
 * @Date：2024/9/1 17:00
 * @Description:
 */
@Data
public class RegisterDto {

    /**
     * 手机号
     */
    @NotEmpty(message = "手机号不能为空")
    @Length(min = 11,max = 11,message = "手机号必须为11位")
    private String phone;


    /**
     * 用户名
     */
    @NotEmpty(message = "用户名不能为空")
    private String username;

    /**
     * 密码
     */
    @NotEmpty(message = "密码不能为空")
    private String password;

    /**
     * 真实姓名
     */
    @NotEmpty(message = "真实姓名不能为空")
    private String name;

    /**
     * 身份证号
     */
    @NotEmpty(message = "身份证号不能为空")
    private String idCard;

    /**
     * 0男 1女 2保密
     */
    @NotEmpty(message = "性别不能为空")
    private Integer sex;

}
