package com.iweb.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.iweb.entity.Users;
import com.iweb.vo.IndexUserInfoVO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author yxy
 * @since 2024-06-28
 */

public interface UsersMapper extends BaseMapper<Users> {

    IndexUserInfoVO getUserInfo(@Param("userId") String userId);

    @Select("SELECT * FROM users WHERE username = #{username} AND password = #{password}")
    Users getByUserNameAndPassword(Users users);
}
