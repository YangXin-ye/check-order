package com.service.impl;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.entity.AccountionRecord;
import com.entity.Classify;
import com.entity.User;
import com.service.AccountionRecordService;
import com.dao.AccountionRecordMapper;
import com.service.ClassifyService;
import com.service.UserService;
import com.vo.DayVo;
import com.vo.SelectByIndexVo;
import com.vo.TotalPriceVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.YearMonth;
import java.util.*;
import java.util.stream.Collectors;

/**
*
* @description 针对表【t_accountion_record(收支明细)】的数据库操作Service实现
* @createDate 2024-10-17 21:44:11
*/
@Service
public class AccountionRecordServiceImpl extends ServiceImpl<AccountionRecordMapper, AccountionRecord>
    implements AccountionRecordService{


    @Autowired
    private UserService userService;

    @Autowired
    private ClassifyService classifyService;
    @Override
    public SelectByIndexVo selectByIndex(Integer mongth, Integer year) {
        User loginUser = userService.getLoginUser();
        SelectByIndexVo selectByIndexVo = new SelectByIndexVo();
        List<AccountionRecord> list = new ArrayList<>();
        if(mongth != null){
            list = this.lambdaQuery()
                    .eq(AccountionRecord::getMonth, mongth)
                    .eq(AccountionRecord::getDelete,0)
                    .eq(AccountionRecord::getUserId,loginUser.getId())
                    .orderByDesc(AccountionRecord::getId).list();
        }
        if(year != null){
            list = this.lambdaQuery()
                    .eq(AccountionRecord::getYear, year)
                    .eq(AccountionRecord::getDelete,0)
                    .eq(AccountionRecord::getUserId,loginUser.getId())
                    .orderByDesc(AccountionRecord::getId).list();
        }
        if(CollectionUtils.isEmpty(list)){
            return selectByIndexVo;
        }
        if(CollectionUtils.isNotEmpty(list)){
            //获取总支出
            BigDecimal totalPay = list.stream().filter(item -> item.getType() == 1)
                    .map(AccountionRecord::getPrice)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            selectByIndexVo.setTotalPay(totalPay);
            //获取总收入
            BigDecimal totalIncome = list.stream().filter(item -> item.getType() == 2)
                    .map(AccountionRecord::getPrice)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            selectByIndexVo.setTotalIncome(totalIncome);

        }
        if(year != null){
            //计算平均支出
            selectByIndexVo.setAverageDayPay(selectByIndexVo.getTotalPay()
                    .divide(new BigDecimal(12),2, BigDecimal.ROUND_HALF_UP));
            //组装详情信息
            List<SelectByIndexVo.DetailInfo> detailInfoList = list.stream()
                    .collect(Collectors.groupingBy(
                            AccountionRecord::getMonth, // 按照月份分组
                            Collectors.groupingBy(
                                    AccountionRecord::getType, // 按照类型分组
                                    Collectors.reducing(BigDecimal.ZERO, AccountionRecord::getPrice, BigDecimal::add) // 计算总金额
                            )
                    ))
                    .entrySet().stream()
                    .flatMap(monthEntry -> monthEntry.getValue().entrySet().stream()
                            .map(typeEntry -> {
                                SelectByIndexVo.DetailInfo detailInfo = new SelectByIndexVo.DetailInfo();
                                detailInfo.setTime(monthEntry.getKey());
                                detailInfo.setType(typeEntry.getKey());
                                detailInfo.setPrice(typeEntry.getValue());
                                return detailInfo;
                            })
                    )
                    .collect(Collectors.toList());
            selectByIndexVo.setDetailInfos(detailInfoList);
        }
        if(mongth != null){
            int daysInMonth = getDaysInMonth(Integer.parseInt(list.get(0).getYear()), mongth);
            String year1 = list.get(0).getYear();
            //计算平均支出
            selectByIndexVo.setAverageDayPay(selectByIndexVo.getTotalPay()
                    .divide(new BigDecimal(daysInMonth),2, BigDecimal.ROUND_HALF_UP));
            //组装详情信息
            List<SelectByIndexVo.DetailInfo> detailInfoList = list.stream()
                    .collect(Collectors.groupingBy(
                            AccountionRecord::getDay, // 按照月份分组
                            Collectors.groupingBy(
                                    AccountionRecord::getType, // 按照类型分组
                                    Collectors.reducing(BigDecimal.ZERO, AccountionRecord::getPrice, BigDecimal::add) // 计算总金额
                            )
                    ))
                    .entrySet().stream()
                    .flatMap(monthEntry -> monthEntry.getValue().entrySet().stream()
                            .map(typeEntry -> {
                                SelectByIndexVo.DetailInfo detailInfo = new SelectByIndexVo.DetailInfo();
                                detailInfo.setTime(monthEntry.getKey());
                                detailInfo.setType(typeEntry.getKey());
                                detailInfo.setPrice(typeEntry.getValue());
                                return detailInfo;
                            })
                    )
                    .collect(Collectors.toList());
            selectByIndexVo.setDetailInfos(detailInfoList);
        }
        return selectByIndexVo;
    }

    @Override
    public DayVo selectByDay(Date day) {
        User loginUser = userService.getLoginUser();
        DayVo dayVo = new DayVo();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        List<AccountionRecord> list = this.lambdaQuery()
                .like(AccountionRecord::getCreateTime,simpleDateFormat.format(day) )
                .eq(AccountionRecord::getUserId,loginUser.getId())
                .eq(AccountionRecord::getDelete,0)
                .orderByDesc(AccountionRecord::getCreateTime)
                .list();
        dayVo.setAccountionRecordList(list);
        if(CollectionUtils.isNotEmpty(list)){
            //获取总支出
            BigDecimal totalPay = list.stream().filter(item -> item.getType() == 1)
                    .map(AccountionRecord::getPrice)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            dayVo.setTotalPay(totalPay);
            //获取总收入
            BigDecimal totalIncome = list.stream().filter(item -> item.getType() == 2)
                    .map(AccountionRecord::getPrice)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            dayVo.setTotalIncome(totalIncome);
        }
        return dayVo;
    }

    @Override
    public TotalPriceVo totalPrice() {
        TotalPriceVo totalPriceVo = new TotalPriceVo();
        User loginUser = userService.getLoginUser();
        List<AccountionRecord> list = this.lambdaQuery()
                .eq(AccountionRecord::getUserId,loginUser.getId())
                .eq(AccountionRecord::getDelete,0)
                .orderByDesc(AccountionRecord::getCreateTime)
                .list();
        //支出
        BigDecimal totalPay = list.stream().filter(item -> item.getType() == 1)
                .map(AccountionRecord::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        //收入
        BigDecimal totalIncome = list.stream().filter(item -> item.getType() == 2)
                .map(AccountionRecord::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        totalPriceVo.setTotalPay(totalPay);
        totalPriceVo.setTotalIncome(totalIncome);

        //支出map
        Map<Integer, List<AccountionRecord>> collect = list.stream()
                .filter(item->item.getType()==1)
                .collect(
                Collectors.groupingBy(AccountionRecord::getClassifyId));
        for (Map.Entry<Integer, List<AccountionRecord>> integerListEntry : collect.entrySet()) {
            Integer key = integerListEntry.getKey();
            List<AccountionRecord> value = integerListEntry.getValue();
            //查询名称
            Classify classify = classifyService.getById(key);
            //组装数据
            TotalPriceVo.TotalPriceVoData totalPriceVoData = new TotalPriceVo.TotalPriceVoData();
            totalPriceVoData.setName(classify.getName());
            totalPriceVoData.setValue(
                    value.stream()
                            .map(AccountionRecord::getPrice)
                            .reduce(BigDecimal.ZERO, BigDecimal::add)
            );
            totalPriceVo.getClassifyTotalPay().add(totalPriceVoData);
        }
        //收入map
        Map<Integer, List<AccountionRecord>> collect2 = list.stream()
                .filter(item->item.getType()==2)
                .collect(
                        Collectors.groupingBy(AccountionRecord::getClassifyId));
        for (Map.Entry<Integer, List<AccountionRecord>> integerListEntry : collect2.entrySet()) {
            Integer key = integerListEntry.getKey();
            List<AccountionRecord> value = integerListEntry.getValue();
            Classify classify = classifyService.getById(key);
            //组装数据
            TotalPriceVo.TotalPriceVoData totalPriceVoData = new TotalPriceVo.TotalPriceVoData();
            totalPriceVoData.setName(classify.getName());
            totalPriceVoData.setValue(
                    value.stream()
                            .map(AccountionRecord::getPrice)
                            .reduce(BigDecimal.ZERO, BigDecimal::add)
            );
            totalPriceVo.getClassifyTotalIncome().add(totalPriceVoData);
        }
        return totalPriceVo;
    }

    @Override
    public void insert(AccountionRecord accountionRecord) {
        //查询类型
        Classify classify = classifyService.lambdaQuery().eq(Classify::getName, accountionRecord.getTypeName()).list().get(0);
        User loginUser = userService.getLoginUser();
        accountionRecord.setClassifyId(classify.getId());
        accountionRecord.setUserId(loginUser.getId());
        accountionRecord.setYear(getYear(accountionRecord.getTime()) + "");
        accountionRecord.setMonth(getMonth(accountionRecord.getTime()) + "");
        accountionRecord.setDay(getDayFromDate(accountionRecord.getTime()) + "");
        accountionRecord.setType(classify.getType());
        this.save(accountionRecord);
    }

    /**
     * 获取指定年月天数
     * @param year
     * @param month
     * @return
     */
    public static int getDaysInMonth(int year, int month) {
        YearMonth yearMonth = YearMonth.of(year, month);
        return yearMonth.lengthOfMonth(); // 返回这个月的天数
    }

    public static int getDayFromDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date); // 设置时间
        return calendar.get(Calendar.DAY_OF_MONTH); // 获取日
    }
    //获取年份天数
    public static int getTotalDaysOfYear(int year) {
        // 判断闰年规则
        if ((year % 4 == 0 && year % 100 != 0) || (year % 400 == 0)) {
            return 366; // 闰年有 366 天
        } else {
            return 365; // 平年有 365 天
        }
    }

    // 获取月份
    public static int getMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.MONTH) + 1; // Java月份从0开始，所以要加1
    }

    // 获取年份
    public static int getYear(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.YEAR);
    }

}




