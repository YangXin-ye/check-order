package com.controller;

import com.entity.AccountionRecord;
import com.entity.Budget;
import com.entity.User;
import com.service.BudgetService;
import com.service.UserService;
import com.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 *
 * @date 2024/10/18 14:52
 */
@RestController
@RequestMapping("/budget/")
public class BudgetController {


    @Autowired
    private BudgetService budgetService;
    @Autowired
    private UserService userService;


    @PostMapping("insert")
    public R insert(@RequestBody Budget budget){
        User loginUser = userService.getLoginUser();
        Budget one = budgetService.lambdaQuery().eq(Budget::getUserId, loginUser.getId())
                .eq(Budget::getType, budget.getType()).one();
        if(one!=null){
            budget.setId(one.getId());
            budgetService.updateById(budget);
        }else{
            budget.setUserId(loginUser.getId());
            budgetService.save(budget);
        }
        return R.ok();
    }


}
