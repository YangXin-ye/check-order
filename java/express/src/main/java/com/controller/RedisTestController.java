package com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * redis测试接口

 * @date 2024/10/24 18:36
 */
@RestController
@RequestMapping("/test/redis")
public class RedisTestController {

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @GetMapping("setRedisData")
    public void setRedisData(){
        redisTemplate.opsForValue().set("testKey","我是一条redis数据");
        System.out.println("redis数据存储成功");
    }

    @GetMapping("getRedisData")
    public void getRedisData(){
        System.out.println("取出redis数据为: " + redisTemplate.opsForValue().get("testKey"));;
    }
}
