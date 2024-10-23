package com.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author zhangguohao
 * @date 2024/10/22 14:24
 */
@Data
@ExcelIgnoreUnannotated
public class TemplateVo {

    @ColumnWidth(25)
    @ExcelProperty(value = "金额(*必填)", index = 0)
    private BigDecimal price;

    /**
     * 分类
     */
    @ColumnWidth(25)
    @ExcelProperty(value = "分类(*必填)", index = 1)
    private String classify;

    /**
     * 备注
     */
    @ColumnWidth(25)
    @ExcelProperty(value = "备注", index = 2)
    private String remark;


    @ColumnWidth(25)
    @ExcelProperty(value = "日期(*必填 格式:年-月-日)",index = 3)
    private String time;
}
