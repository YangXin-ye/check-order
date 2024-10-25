package com.controller;

import com.dto.LoginDto;
import com.dto.RegisterDto;
import com.dto.UpdateUserDto;
import com.entity.District;
import com.entity.User;
import com.service.DistrictService;
import com.service.UserService;
import com.utils.R;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @Author：
 * @Package：com.controller
 * @Project：express
 * @name：UserController
 * @Date：2024/9/1 16:59
 * @Description:
 */
@RestController
@RequestMapping("/user")
public class UserController {


    @Autowired
    private UserService userService;

    @Autowired
    private DistrictService districtService;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;


    /**
     * 登陆接口
     * @param loginDto
     * @return
     */
    @PostMapping("/login")
    public R login(@RequestBody @Validated LoginDto loginDto){
        String token = userService.login(loginDto);
        return R.ok().put("token",token);
    }

    @PostMapping("/register")
    public R register(@RequestBody @Validated User user){
        //校验验证码
        String userRegisterCode = redisTemplate.opsForValue().get("registerEmail::" + user.getEmail());
        if(StringUtils.isEmpty(userRegisterCode)){
            //如果redis中没有验证码，那么证明他没有获取验证码
            return R.error("清先获取邮箱验证码");
        }
        if(!userRegisterCode.equals(user.getEmailCode())){
            //如果验证码和用户输入的验证码不一致，提示验证码错误
            return R.error("验证码错误");
        }
        if(user.getAddressList().size()==2){
            //查询地址中的省份
            District provice = districtService.getById(user.getAddressList().get(0));
            //查询地址中的城市
            District city = districtService.getById(user.getAddressList().get(1));
            //设置地址
            user.setAddress(provice.getName()+city.getName());
        }else{
            //查询地址中的省份
            District provice = districtService.getById(user.getAddressList().get(0));
            //查询地址中的城市
            District city = districtService.getById(user.getAddressList().get(1));
            //查询地址中的区
            District area = districtService.getById(user.getAddressList().get(2));
            //设置地址
            user.setAddress(provice.getName()+city.getName()+area.getName());
        }
        //保存用户信息
        userService.save(user);
        return R.ok();
    }

    /**
     * 获取验证码
     * @param phone
     * @return
     */
    @GetMapping("/sendVerificationCode")
    public R sendVerificationCode(@RequestParam("phone") String phone){
        userService.sendVerificationCode(phone);
        return R.ok();
    }

    /**
     * 发送邮箱验证码
     * @param email
     * @return
     */
    @GetMapping("/sendEmailCode")
    public R sendEmailCode(@RequestParam("email") String email){
        userService.sendEmailCoe(email);
        return R.ok();
    }

    /**
     * 获取当前登陆用户接口
     * @return
     */
    @GetMapping("/getLoginUser")
    public R getLoginUser(){
        User user = userService.getLoginUser();
        return R.ok().put("user",user);
    }

    /**
     * 更新当前登陆用户信息
     * @param updateUserDto
     * @return
     */
    @PostMapping("/updateLoginUser")
    public R updateLoginUser(@RequestBody UpdateUserDto updateUserDto){
        userService.updateLoginUser(updateUserDto);
        return R.ok();
    }

}
