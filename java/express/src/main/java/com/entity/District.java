package com.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @date 2024/10/25 22:08
 */
@Data
@TableName("district")
public class District {

    @TableId
    private String id;

    @TableField("parent_id")
    private String parentId;

    @TableField("city_code")
    private String cityCode;

    @TableField("adcode")
    private String adcode;

    @TableField("name")
    private String name;

    @TableField("polyline")
    private String polyline;

    @TableField("center")
    private String center;

    @TableField("level")
    private String level;

    @TableField("remark")
    private String remark;

    @TableField("create_by")
    private String createBy;

    @TableField("create_time")
    private Date createTime;

    @TableField("update_by")
    private String updateBy;

    @TableField("update_time")
    private Date updateTime;

    @TableField("deleted")
    private Integer deleted;
}
