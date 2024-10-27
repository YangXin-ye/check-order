package com.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dto.LoginDto;
import com.dto.RegisterDto;
import com.dto.UpdateUserDto;
import com.entity.User;
import com.exception.BizException;
import com.service.EmailSender;
import com.service.UserService;
import com.dao.UserMapper;
import com.utils.SendMsgUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
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
    implements UserService{

    @Autowired
    private RedisTemplate<String,String> redisTemplate;


    @Autowired
    private HttpServletRequest request;

    @Autowired
    private EmailSender emailSender;
    @Override
    public String login(LoginDto loginDto) {
        Integer loginType = loginDto.getLoginType();
        User user;
        switch (loginType){
            case 1:
                if(StringUtils.isEmpty(loginDto.getPhone())){
                    throw new BizException("登陆手机号不能为空");
                }
                //手机号登陆
                user = this.lambdaQuery().eq(User::getPhone, loginDto.getPhone()).eq(User::getDelete,0).one();
                if(user == null){
                    throw new BizException("用户手机号不存在");
                }
                String code = redisTemplate.opsForValue().get("loginPhone::" + user.getPhone());
                if(StringUtils.isEmpty(code) || !loginDto.getCode().equals(code)){
                    throw new BizException("验证码不正确");
                }
                break;
            case 2:
                if(StringUtils.isEmpty(loginDto.getUsername()) || StringUtils.isEmpty(loginDto.getPassword())){
                    throw new BizException("用户名或者密码不能为空");
                }
                //账号密码登陆
                user = this.lambdaQuery().eq(User::getUsername, loginDto.getUsername()).eq(User::getDelete,0).one();
                if(user == null){
                    throw new BizException("用户名不存在");
                }
                //校验密码
                String password = user.getPassword();
                if(!loginDto.getPassword().equals(password)){
                    throw new BizException("密码不正确，请重新输入");
                }
                break;
            default:
                throw new BizException("登陆类型异常");
        }
        //生成token
        String token = UUID.randomUUID().toString().replace("-","");
        //存储用户信息
        user.setPassword(null);
        redisTemplate.opsForValue().set("loginToken::"+token, JSON.toJSONString(user));
        return token;
    }

    @Override
    public User getLoginUser() {
        String loginToken = request.getHeader("authorization");
        if(StringUtils.isEmpty(loginToken)){
            throw new BizException("token不能为空");
        }
        String userInfo = redisTemplate.opsForValue().get("loginToken::" + loginToken);
        if(StringUtils.isEmpty(userInfo)){
            throw new BizException("token无用户信息");
        }
        User user = JSON.parseObject(userInfo, User.class);
        Integer id = user.getId();
        User user2 = this.getById(id);
        user2.setPassword(null);
        return user2;
    }



    @Override
    public void updateLoginUser(UpdateUserDto updateUserDto) {
        String loginToken = request.getHeader("token");
        User loginUser = getLoginUser();
        Integer id = loginUser.getId();
        User user = new User();
        BeanUtils.copyProperties(updateUserDto,user);
        user.setPhoto(updateUserDto.getImg());
        user.setId(id);
        if(StringUtils.isEmpty(updateUserDto.getPassword())){
            User selUser = this.getById(id);
            user.setPassword(selUser.getPassword());
        }
        //更新
        this.updateById(user);
        //查询新的用户信息
        User newUser = this.getById(id);
        //刷新redis
        redisTemplate.opsForValue().set("loginToken::"+loginToken, JSON.toJSONString(newUser));
    }

    @Override
    public String sendVerificationCode(String phone) {
        User userForPhone = this.lambdaQuery().eq(User::getPhone, phone).eq(User::getDelete,0).one();
        if(userForPhone == null){
            throw new BizException("用户手机号不存在，请先去注册");
        }
        String phoneCode = redisTemplate.opsForValue().get("loginPhone::" + phone);
        if(StringUtils.isNotEmpty(phoneCode)){
            throw new BizException("请勿重复获取验证码");
        }
        //生成验证码存储到redis中
        String s = String.valueOf(generateFourDigitRandomNumber());
        //发送验证码
        try {
            SendMsgUtil.sendCode(phone,s);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("验证码发送失败 e={}",e);
        }
        redisTemplate.opsForValue().set("loginPhone::" + phone,s,1, TimeUnit.MINUTES);
        System.out.println(phone+" 手机获取验证码，验证码 = "+s);
        return s;
    }

    @Override
    public void sendEmailCoe(String email) {
        String code = String.valueOf(generateFourDigitRandomNumber());
        redisTemplate.opsForValue().set("registerEmail::" + email,code,5, TimeUnit.MINUTES);
        try {
            emailSender.sendVerificationCode(email,code);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }


    public void selectByName(String name){
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper.like(User::getName,name);
        List<User> list = this.list(lambdaQueryWrapper);
    }

    public void upUser(){
        LambdaUpdateWrapper<User> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        lambdaUpdateWrapper.set(User::getPassword,"123456");
        lambdaUpdateWrapper.like(User::getName,"张");
        this.update(lambdaUpdateWrapper);
    }

    public void delete(){
        LambdaQueryWrapper<User> userLambdaQueryWrapper = new LambdaQueryWrapper<>();
        userLambdaQueryWrapper.like(User::getName,"张");
        remove(userLambdaQueryWrapper);
    }
    public int generateFourDigitRandomNumber() {
        return ThreadLocalRandom.current().nextInt(1000, 10000);
    }


}




