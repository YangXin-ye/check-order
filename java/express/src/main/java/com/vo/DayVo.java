package com.vo;

import com.entity.AccountionRecord;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 
 * @date 2024/10/18 14:15
 */
@Data
public class DayVo {


    private List<AccountionRecord> accountionRecordList;

    //总收入
    private BigDecimal totalIncome;

    //总支出
    private BigDecimal totalPay;
}
