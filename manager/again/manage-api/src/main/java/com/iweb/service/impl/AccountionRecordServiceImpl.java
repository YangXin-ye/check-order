package com.iweb.service.impl;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.util.StringUtil;
import com.iweb.entity.AccountionRecord;
import com.iweb.entity.Classify;
import com.iweb.entity.ListUserPageReq;
import com.iweb.entity.User;
import com.iweb.mapper.AccountionRecordMapper;
import com.iweb.service.AccountionRecordService;
import com.iweb.service.ClassifyService;
import com.iweb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.YearMonth;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @description 针对表【t_accountion_record(收支明细)】的数据库操作Service实现
 * @createDate 2024-10-17 21:44:11
 */
@Service
public class AccountionRecordServiceImpl extends ServiceImpl<AccountionRecordMapper, AccountionRecord>
        implements AccountionRecordService {


    @Autowired
    private UserService userService;
    @Autowired
    private ClassifyService classifyService;

    @Override
    public Page<AccountionRecord> accountionRecordList(Integer pageNum, Integer pageSize, ListUserPageReq listUserPageReq) {
        LambdaQueryWrapper<AccountionRecord> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if (null != listUserPageReq.getType() && listUserPageReq.getType() != 0)  {
            lambdaQueryWrapper.eq(AccountionRecord::getType, listUserPageReq.getType());
        }
        if (StringUtil.isNotEmpty(listUserPageReq.getRemark())) {
            lambdaQueryWrapper.like(AccountionRecord::getRemark, listUserPageReq.getRemark());
        }
        lambdaQueryWrapper.eq(AccountionRecord::getDelete, 0);
        Page<AccountionRecord> page = new Page<>(pageNum, pageSize);
        Page<AccountionRecord> resultPage = baseMapper.selectPage(page, lambdaQueryWrapper);
        List<AccountionRecord> records = resultPage.getRecords();
        for (AccountionRecord record : records) {
            User user = userService.getById(record.getUserId());
            record.setUserName(user.getName());
            Classify classify = classifyService.getById(record.getClassifyId());
            record.setClassifyName(classify.getName());
        }
        resultPage.setRecords(records);
        return resultPage;
    }

    @Override
    public Page<AccountionRecord> delList(Integer pageNum, Integer pageSize, ListUserPageReq listUserPageReq) {
        LambdaQueryWrapper<AccountionRecord> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if (null != listUserPageReq.getType() && listUserPageReq.getType() != 0) {
            lambdaQueryWrapper.eq(AccountionRecord::getType, listUserPageReq.getType());
        }
        if (StringUtil.isNotEmpty(listUserPageReq.getRemark())) {
            lambdaQueryWrapper.like(AccountionRecord::getRemark, listUserPageReq.getRemark());
        }
        lambdaQueryWrapper.eq(AccountionRecord::getDelete, 1);
        Page<AccountionRecord> page = new Page<>(pageNum, pageSize);
        Page<AccountionRecord> resultPage = baseMapper.selectPage(page, lambdaQueryWrapper);
        List<AccountionRecord> records = resultPage.getRecords();
        for (AccountionRecord record : records) {
            User user = userService.getById(record.getUserId());
            record.setUserName(user.getName());
            Classify classify = classifyService.getById(record.getClassifyId());
            record.setClassifyName(classify.getName());
        }
        resultPage.setRecords(records);
        return resultPage;
    }

    @Override
    public Boolean restore(Integer id) {
        if (id == null) {
            throw new RuntimeException("id不能为空");

        }
        AccountionRecord accountionRecord = this.getById(id);
        if (accountionRecord == null) {
            throw new RuntimeException("信息不存在");
        }
        AccountionRecord upAccountionRecord = new AccountionRecord();
        upAccountionRecord.setId(id);
        upAccountionRecord.setDelete(0);
        return updateById(upAccountionRecord);
    }
}




