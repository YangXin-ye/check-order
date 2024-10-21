package com.iweb.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.iweb.entity.ListUserPageReq;
import com.iweb.entity.User;
import com.iweb.entity.Users;

/**

* @description 针对表【user】的数据库操作Service
* @createDate 2024-09-01 16:57:43
*/
public interface UserService extends IService<User> {


    Page<User> userList(Integer pageNum, Integer pageSize, ListUserPageReq listUserPageReq);

    Boolean insertUser(User user);

    Boolean deleteUser(Integer userId);

    Boolean updateUser(User user);
}
