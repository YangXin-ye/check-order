package com.iweb.service.impl;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.util.StringUtil;
import com.iweb.entity.ListUserPageReq;
import com.iweb.entity.User;
import com.iweb.entity.Users;
import com.iweb.mapper.UserMapper;
import com.iweb.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**

* @description 针对表【user】的数据库操作Service实现
* @createDate 2024-09-01 16:57:43
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService {


    @Override
    public Page<User> userList(Integer pageNum, Integer pageSize, ListUserPageReq listUserPageReq) {
        LambdaQueryWrapper<User> lambdaQuery = new LambdaQueryWrapper<>();
        if(listUserPageReq != null && StringUtil.isNotEmpty(listUserPageReq.getUsername())){
            lambdaQuery.like(User::getUsername,listUserPageReq.getUsername());
        }
        if(listUserPageReq != null && StringUtil.isNotEmpty(listUserPageReq.getName()))
        {
            lambdaQuery.like(User::getName,listUserPageReq.getName());
        }
        lambdaQuery.eq(User::getDelete,0);
        Page<User> page = new Page<>(pageNum, pageSize);
        Page<User> resultPage = baseMapper.selectPage(page, lambdaQuery);
        return resultPage;
    }

    @Override
    public Boolean insertUser(User user) {
        if(StringUtil.isEmpty(user.getUsername())){
            throw new RuntimeException("用户名不能为空");
        }
        if(StringUtil.isEmpty(user.getPassword())){
            throw new RuntimeException("密码不能为空");
        }
        if(StringUtil.isEmpty(user.getPhone())){
            throw new RuntimeException("手机号不能为空");
        }
        user.setDelete(0);
        return this.save(user);
    }

    @Override
    public Boolean deleteUser(Integer userId) {
        if(userId == null){
            throw new RuntimeException("用户Id不能为空");
        }
        User user = getById(userId);
        if(user == null){
            throw new RuntimeException("用户不存在");
        }
       return this.lambdaUpdate().set(User::getDelete,1)
                .eq(User::getId,userId).update();
    }

    @Override
    public Boolean updateUser(User upUser) {
        if(upUser == null){
            throw new RuntimeException("用户信息不存在");
        }
        if(upUser.getId() == null){
            throw new RuntimeException("用户Id不能为空");
        }
        User user = getById(upUser.getId());
        if(user == null){
            throw new RuntimeException("用户不存在");
        }
        if(StringUtil.isEmpty(upUser.getUsername())){
            throw new RuntimeException("用户名不能为空");
        }
        if(StringUtil.isEmpty(upUser.getPassword())){
            throw new RuntimeException("密码不能为空");
        }
        if(StringUtil.isEmpty(upUser.getPhone())){
            throw new RuntimeException("手机号不能为空");
        }
        return this.updateById(upUser);
    }
}




