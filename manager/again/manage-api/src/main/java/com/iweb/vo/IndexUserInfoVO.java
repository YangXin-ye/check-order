package com.iweb.vo;

import lombok.Data;

import java.security.Timestamp;

/**
 * @Author 
 * @Create 2024-06-28-15:06
 * @Message
 **/
@Data
public class IndexUserInfoVO {

    private Integer uId;
    private Integer aId;
    private String username;
    private String avatar;
    private String role;
    private Timestamp time;
    private String address;

    private String name;

}
