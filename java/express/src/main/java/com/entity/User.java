package com.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

/**
 *
 * @TableName user
 */
@TableName(value ="t_user")
@Data
public class User implements Serializable {
    /**
     *
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 用户名
     */
    @TableField("username")
    private String username;

    /**
     * 密码
     */
    @TableField("password")
    private String password;

    /**
     * 手机号
     */
    @TableField("phone")
    private String phone;

    /**
     * 真实姓名
     */
    @TableField("name")
    private String name;

    /**
     * 0男 1女 2保密
     */
    @TableField("sex")
    private Integer sex;

    /**
     * 年龄
     */
    @TableField("age")
    private Integer age;

    @TableField("photo")
    private Long photo;

    private String email;

    private String address;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField("create_time")
    private Date createTime;


    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField("update_time")
    private Date updateTime;


    /**
     * 0未删除1已删除
     */
    @TableField("`delete`")
    private Integer delete;

    @TableField(exist = false)
    private List<String> addressList;

    @TableField(exist = false)
    private String emailCode;

}
