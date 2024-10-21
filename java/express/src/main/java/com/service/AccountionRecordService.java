package com.service;

import com.entity.AccountionRecord;
import com.baomidou.mybatisplus.extension.service.IService;
import com.vo.DayVo;
import com.vo.SelectByIndexVo;
import com.vo.TotalPriceVo;

import java.util.Date;

/**
* 
* @description 针对表【t_accountion_record(收支明细)】的数据库操作Service
* @createDate 2024-10-17 21:44:11
*/
public interface AccountionRecordService extends IService<AccountionRecord> {

    SelectByIndexVo selectByIndex(Integer mongth, Integer year);

    DayVo selectByDay(Date day);

    TotalPriceVo totalPrice();

    void insert(AccountionRecord accountionRecord);

}
