package com.iweb.controller;

import com.iweb.config.Result;
import com.iweb.entity.ListUserPageReq;
import com.iweb.entity.User;
import com.iweb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

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

    /**
     * 分页查询所有数据
     *
     * @param listUserPageReq 请求体
     * @param pageNum         当前页
     * @param pageSize        每页大小
     */
    @GetMapping("/list")
    public Result userList(ListUserPageReq listUserPageReq,
                               @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                               @RequestParam(value = "pageSize", defaultValue = "20") Integer pageSize) {

        return Result.success(userService.userList(pageNum,pageSize,listUserPageReq));
    }

    @PostMapping("/insert")
    public Result insertUser(@RequestBody User user) {
        return Result.success(userService.insertUser(user));
    }

    @GetMapping("/delete")
    public Result deleteUser(@RequestParam Integer id) {
        return Result.success(userService.deleteUser(id));
    }

    @PostMapping("/update")
    public Result updateUser(@RequestBody User user) {
        return Result.success(userService.updateUser(user));
    }
}
