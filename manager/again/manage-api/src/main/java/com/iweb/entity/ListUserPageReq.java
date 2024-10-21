package com.iweb.entity;

import lombok.Data;

/**
 * @Author 
 * @Create 2024-06-07-15:32
 * @Message
 **/
@Data
public class ListUserPageReq {


    private String name;

    private String username;


    private String remark;

    /**
     * 1支出 2收入
     */
    private Integer type;


}
