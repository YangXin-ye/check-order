package com.controller;

import com.service.RabbitMqSender;
import com.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhangguohao
 * @date 2024/10/23 22:50
 */
@RestController
@RequestMapping("/mq/test")
public class MqTestController {

    @Autowired
    private RabbitMqSender rabbitMqSender;

    @RequestMapping("/sendMsg")
    public R sendMsg(){
        rabbitMqSender.send("testExchange","testRoutingKey","我是一条消息");
        return R.ok();
    }
}
