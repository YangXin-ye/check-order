package com.controller;

import com.entity.Bank;
import com.entity.User;
import com.service.BankService;
import com.service.UserService;
import com.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * @date 2024/10/18 12:50
 */
@RestController
@RequestMapping("bank")
public class BankController {

    @Autowired
    private BankService bankService;

    @Autowired
    private UserService userService;

    @PostMapping("insert")
    public R insert(@RequestBody Bank bank) {
        User loginUser = userService.getLoginUser();
        bank.setUserId(loginUser.getId());
        bank.setCreateTime(new Date());
        bank.setBankType("储蓄卡");
        switch (bank.getName()){
            case "中国工商银行":
                bank.setBankLogo("gs");
                bank.setLimitNum("20万元");
                bank.setBankBg("card-red");
                break;
            case "中国建设银行":
                bank.setBankLogo("jh");
                bank.setLimitNum("20万元");
                bank.setBankBg("card-blue");
                break;
            case "农业银行":
                bank.setBankLogo("nh");
                bank.setLimitNum("20万元");
                bank.setBankBg("card-green");
                break;
        }
        bankService.save(bank);
        return R.ok();
    }

    @PostMapping("delete")
    public R delete(@RequestBody Bank bank) {
        bankService.lambdaUpdate().eq(Bank::getId,bank.getId())
                .set(Bank::getDelete,1).update();
        return R.ok();
    }

    @PostMapping("update")
    public R update(@RequestBody Bank bank) {
        switch (bank.getName()){
            case "中国工商银行":
                bank.setBankLogo("gs");
                bank.setLimitNum("20万元");
                bank.setBankBg("card-red");
                break;
            case "中国建设银行":
                bank.setBankLogo("jh");
                bank.setLimitNum("20万元");
                bank.setBankBg("card-blue");
                break;
            case "农业银行":
                bank.setBankLogo("nh");
                bank.setLimitNum("20万元");
                bank.setBankBg("card-green");
                break;
        }
        bankService.updateById(bank);
        return R.ok();
    }

    @GetMapping("select")
    public R select() {
        User user = userService.getLoginUser();
        List<Bank> list = bankService.lambdaQuery()
                .eq(Bank::getDelete,0)
                .eq(Bank::getUserId,user.getId())
                .list();
//        for (Bank bank : list) {
//            if(bank.getBankNum().length()>4){
//                bank.setBankNum(bank.getBankNum().substring(0,4));
//            }
//        }
        return R.ok().put("data", list);
    }
}
