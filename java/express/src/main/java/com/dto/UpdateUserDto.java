package com.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @Author：
 * @Package：com.dto
 * @Project：express
 * @name：UpdateUserDto
 * @Date：2024/9/1 17:34
 * @Description:
 */
@Data
public class UpdateUserDto {


    /**
     * 手机号
     */
    private String phone;

    /**
     * 真实姓名
     */
    private String name;

    /**
     * 身份证号
     */
    private String idCard;

    /**
     * 头像
     */
    private String head;

    /**
     * 0男 1女 2保密
     */
    private Integer sex;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 真实图片
     */
    private Long img;

    private String password;

}
