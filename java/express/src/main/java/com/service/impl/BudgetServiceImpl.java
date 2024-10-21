package com.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.entity.Budget;
import com.service.BudgetService;
import com.dao.BudgetMapper;
import org.springframework.stereotype.Service;

/**
* 
* @description 针对表【t_budget(预算设置表)】的数据库操作Service实现
* @createDate 2024-10-17 21:44:11
*/
@Service
public class BudgetServiceImpl extends ServiceImpl<BudgetMapper, Budget>
    implements BudgetService{

}




