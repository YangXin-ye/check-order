package com.iweb.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iweb.entity.ListUserPageReq;
import com.iweb.entity.Users;
import com.baomidou.mybatisplus.extension.service.IService;
import com.iweb.vo.IndexUserInfoVO;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yxy
 * @since 2024-06-28
 */
public interface UsersService extends IService<Users> {

    IndexUserInfoVO getUserInfo();

    Page<Users> listUserPage(Integer pageNum, Integer pageSize, ListUserPageReq listUserPageReq);

    void saveUser(Users users);

    void updateUser(Users users);

    void deleteUser(Integer id);

    Users login(Users users);
}
