package com.iweb.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.iweb.entity.AccountionRecord;
import com.iweb.entity.ListUserPageReq;

import java.util.Date;

/**
*
* @description 针对表【t_accountion_record(收支明细)】的数据库操作Service
* @createDate 2024-10-17 21:44:11
*/
public interface AccountionRecordService extends IService<AccountionRecord> {

    Page<AccountionRecord> accountionRecordList(Integer pageNum, Integer pageSize, ListUserPageReq listUserPageReq);

    Page<AccountionRecord> delList(Integer pageNum, Integer pageSize, ListUserPageReq listUserPageReq);

    Boolean restore(Integer id);
}
