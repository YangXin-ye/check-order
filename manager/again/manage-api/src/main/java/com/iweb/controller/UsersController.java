package com.iweb.controller;


import com.iweb.config.Result;
import com.iweb.entity.ListUserPageReq;
import com.iweb.entity.Users;
import com.iweb.service.UsersService;
import com.iweb.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author yxy
 * @since 2024-06-28
 */
@RestController
@RequestMapping("/users")
public class UsersController {

    @Resource
    private UsersService usersService;

    @GetMapping("/userInfo")
    public Result getUserInfo(@RequestHeader String token){
        Claims claims = JwtUtils.parseJwt(token);
        String loginId = claims.get("id").toString();
        Users loginUser = usersService.getById(Integer.parseInt(loginId));
        return Result.success(loginUser);
    }

    /**
     * 分页查询所有数据
     *
     * @param listUserPageReq 请求体
     * @param pageNum         当前页
     * @param pageSize        每页大小
     */
    @GetMapping("/list")
    public Result listUserPage(ListUserPageReq listUserPageReq,
                               @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                               @RequestParam(value = "pageSize", defaultValue = "20") Integer pageSize) {

        return Result.success(usersService.listUserPage(pageNum,pageSize,listUserPageReq));
    }

    @PostMapping("/saveUser")
    public Result saveUser(@RequestBody Users users){
        usersService.saveUser(users);
        return Result.success();
    }

    @PostMapping("/updateUser")
    public Result updateUser(@RequestBody Users users){
        usersService.updateUser(users);
        return Result.success();
    }

    @GetMapping("/deleteUser/{id}")
    public Result deleteUser(@PathVariable("id") Integer id){
        usersService.deleteUser(id);
        return Result.success();
    }
}
