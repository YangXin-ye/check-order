package com.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.entity.Bank;
import com.service.BankService;
import com.dao.BankMapper;
import org.springframework.stereotype.Service;

/**
* @description 针对表【t_bank】的数据库操作Service实现
* @createDate 2024-10-20 01:02:05
*/
@Service
public class BankServiceImpl extends ServiceImpl<BankMapper, Bank>
    implements BankService{

}




