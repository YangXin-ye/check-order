package com.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 导入数据Vo
 * @author zhangguohao
 * @date 2024/10/23 20:42
 */
@Data
public class ImportDataVo {

    @ExcelProperty("金额(*必填)")
    private BigDecimal price;

    @ExcelProperty("分类(*必填)")
    private String category;

    @ExcelProperty("日期(*必填 格式:年-月-日)")
    private String date;

    @ExcelProperty("备注")
    private String remark;
}

