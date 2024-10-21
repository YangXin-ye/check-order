package com.iweb.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 收支明细
 * @TableName t_accountion_record
 */
@TableName(value ="t_accountion_record")
@Data
public class AccountionRecord implements Serializable {
    /**
     *
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 用户Id
     */
    private Integer userId;

    /**
     * 分类Id
     */
    private Integer classifyId;

    /**
     * 1支出 2收入
     */
    @ExcelProperty(value = "类型",order = 2)
    private Integer type;

    /**
     * 金额
     */
    @ExcelProperty(value = "金额",order = 3)
    private BigDecimal price;

    /**
     * 备注
     */

    @ExcelProperty(value = "备注",order = 4)
    private String remark;

    /**
     * 创建时间
     */

    @ExcelProperty(value = "创建时间",order = 5)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 修改时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    /**
     * 0未删除1删除
     */
    @TableField("`delete`")
    private Integer delete;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;


    private String year;

    private String month;

    private String day;

    @TableField(exist = false)
    @ExcelProperty(value = "用户名称",order = 0)
    private String userName;

    @TableField(exist = false)
    @ExcelProperty(value = "分类名称",order = 1)
    private String classifyName;
}
