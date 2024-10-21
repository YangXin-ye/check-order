package com.controller;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.entity.AccountionRecord;
import com.entity.Budget;
import com.entity.Classify;
import com.entity.User;
import com.github.pagehelper.util.StringUtil;
import com.service.AccountionRecordService;
import com.service.BudgetService;
import com.service.UserService;
import com.utils.R;
import com.vo.DayVo;
import com.vo.SelectByIndexVo;
import com.vo.TotalPriceVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.annotation.HttpConstraint;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 *
 * @date 2024/10/18 12:45
 */
@RestController
@RequestMapping("accountion/record")
public class AccountionRecordController {

    @Autowired
    private UserService userService;

    @Autowired
    private AccountionRecordService accountionRecordService;
    @Autowired
    private BudgetService budgetService;


    @PostMapping("insert")
    public R insert(@RequestBody AccountionRecord accountionRecord){
        accountionRecordService.insert(accountionRecord);
        return R.ok();
    }

    /**
     * 首页数据
     * @param mongth
     * @param year
     * @return
     */
    @GetMapping("selectByIndex")
    public R selectByIndex(@RequestParam(required = false) Integer mongth,
                           @RequestParam(required = false) Integer year){
        SelectByIndexVo selectByIndexVo = accountionRecordService.selectByIndex(mongth,year);
        return R.ok().put("data",selectByIndexVo);
    }

    /**
     * 某个月的明细
     * @param mongth
     * @return
     */
    @GetMapping("selectByDetail")
    public R selectByDetail(Integer mongth){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        User loginUser = userService.getLoginUser();
        List<AccountionRecord> list = accountionRecordService.lambdaQuery()
                .eq(AccountionRecord::getDelete,0)
                .eq(AccountionRecord::getUserId,loginUser.getId())
                .eq(AccountionRecord::getMonth, mongth)
                .eq(AccountionRecord::getYear, sdf.format(new Date()))
                .orderByDesc(AccountionRecord::getCreateTime)
                .list();
        return R.ok().put("data",list);
    }

    /**
     * 某一天的明细数据
     * @return
     */
    @GetMapping("selectByDay")
    public R selectByDay(){
        DayVo dayVo = accountionRecordService.selectByDay(new Date());
        return R.ok().put("data",dayVo);
    }


    /**
     * 收支记录
     * @return
     */
    @GetMapping("list")
    public R list(@RequestParam String remark){
        User loginUser = userService.getLoginUser();
        if(StringUtil.isEmpty(remark)){
            List<AccountionRecord> list = accountionRecordService.lambdaQuery()
                    .eq(AccountionRecord::getUserId,loginUser.getId())
                    .orderByDesc(AccountionRecord::getId).list();
            return R.ok().put("data",list);
        }else{
            List<AccountionRecord> list = accountionRecordService.lambdaQuery()
                    .eq(AccountionRecord::getUserId,loginUser.getId())
                    .like(AccountionRecord::getRemark,remark)
                    .orderByDesc(AccountionRecord::getId)
                    .list();
            return R.ok().put("data",list);
        }

    }

    /**
     * 饼图展示
     * @return
     */
    @GetMapping("totalPrice")
    public R totalPrice(){
        TotalPriceVo totalPriceVo = accountionRecordService.totalPrice();
        return R.ok().put("data",totalPriceVo);
    }


    @GetMapping("delete")
    public R delete(@RequestParam Integer id){
        accountionRecordService.lambdaUpdate().set(AccountionRecord::getDelete,1)
                .eq(AccountionRecord::getId,id).update();
        return R.ok();
    }

    @GetMapping("getBudgetExceed")
    public R getBudgetExceed(){
        User loginUser = userService.getLoginUser();
        // 获取当前日期
        LocalDate currentDate = LocalDate.now();
        // 获取当前月份（1-12）
        int currentMonth = currentDate.getMonthValue();
        List<AccountionRecord> list = accountionRecordService.lambdaQuery()
                .eq(AccountionRecord::getUserId, loginUser.getId())
                .eq(AccountionRecord::getType, 1)
                .eq(AccountionRecord::getDelete, 0)
                .eq(AccountionRecord::getMonth,currentMonth).list();
        BigDecimal totalPay = list.stream()
                .map(AccountionRecord::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        List<Budget> budgets = budgetService.lambdaQuery().eq(Budget::getUserId, loginUser.getId())
                .eq(Budget::getType, 2).list();
        if(CollectionUtils.isEmpty(budgets)){
            return R.ok();
        }
        if(totalPay.doubleValue()>budgets.get(0).getBudgetPrice().doubleValue()){
            return R.ok().put("data","您本月预算为"
            +budgets.get(0).getBudgetPrice().doubleValue() +"元，已经支出"+
                    totalPay.doubleValue()+" 元，请注意！");
        }
        return R.ok();
    }


}
