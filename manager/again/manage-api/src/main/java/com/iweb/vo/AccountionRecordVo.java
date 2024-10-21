package com.iweb.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 收支明细
 * @TableName t_accountion_record
 */
@TableName(value ="t_accountion_record")
@Data
public class AccountionRecordVo implements Serializable {


    /**
     * 1支出 2收入
     */
    @ExcelProperty(value = "类型",order = 2)
    private String type;

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


    @TableField(exist = false)
    @ExcelProperty(value = "用户名称",order = 0)
    private String userName;

    @TableField(exist = false)
    @ExcelProperty(value = "分类名称",order = 1)
    private String classifyName;
}
