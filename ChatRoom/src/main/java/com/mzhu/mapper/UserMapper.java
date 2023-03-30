package com.mzhu.mapper;

import com.mzhu.pojo.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;
/**
 * 登录要求：判定用户名是否存在和密码是否一致
 * 注册要求：首先判定用户名是否存在
 * 1、若存在，则添加失败
 * 2、若不存在，继续insert的操作，添加成功
 * 修改密码要求：首先判断用户名是否存在
 * 1、若存在，继续进行修改操作
 * 2、若不存在，修改失败
 *
 *
 *
 */


public interface UserMapper {

    List<User> selectAll();
    User selectByUsername(String username);
    //To insert a new user while register
    int addUser(User newUser);
    //To change user's password
    void updatePassword(@Param("password") String password,@Param("username") String username);




}
