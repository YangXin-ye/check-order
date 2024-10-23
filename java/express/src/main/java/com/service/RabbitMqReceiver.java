package com.service;

import com.alibaba.fastjson.JSON;
import com.entity.AccountionRecord;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @author zhangguohao
 * @date 2024/10/23 22:32
 */
@Service
public class RabbitMqReceiver {


    @Autowired
    private AccountionRecordService accountionRecordService;

    @RabbitListener(queues = "testQueue")
    public void receive(String message) {
        System.out.println("我收到了一条消息,消息内容为: " + message);
    }

    @RabbitListener(queues = "importDataQueue")
    public void importData(String message) {
        System.out.println("我收到了一条消息,消息内容为: " + message);
        try {
            if(!StringUtils.isEmpty(message)){
                List<AccountionRecord> accountionRecordList = JSON.parseArray(message, AccountionRecord.class);
                for (AccountionRecord accountionRecord : accountionRecordList) {
                    accountionRecordService.insert(accountionRecord);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
