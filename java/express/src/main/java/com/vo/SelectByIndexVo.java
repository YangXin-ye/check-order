package com.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @date 2024/10/18 13:00
 */
@Data
public class SelectByIndexVo {

    //总收入
    private BigDecimal totalIncome = new BigDecimal("0");

    //总支出
    private BigDecimal totalPay = new BigDecimal("0");

    //平均每日支出
    private BigDecimal averageDayPay = new BigDecimal("0");

    private List<DetailInfo> detailInfos = new ArrayList<>();

    @Data
    public static class DetailInfo{
        //1、1支出 2收入
        private Integer type;

        //金额
        private BigDecimal price;

        private String time;
    }


}
