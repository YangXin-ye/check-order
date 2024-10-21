package com.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @date 2024/10/18 14:20
 */
@Data
public class TotalPriceVo {

    //每一个分类的总收入
    private List<TotalPriceVoData> classifyTotalIncome = new ArrayList<>();

    //每一份分类的总支出
    private List<TotalPriceVoData>  classifyTotalPay = new ArrayList<>();


    //总收入
    private BigDecimal totalIncome;

    //总支出
    private BigDecimal totalPay;

    @Data
    public static class TotalPriceVoData{
        private String name;

        private BigDecimal value;
    }
}
