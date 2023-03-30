package com.mzhu.UIAndBackEnd.BackEnd;

import com.mzhu.mapper.UserMapper;
import com.mzhu.pojo.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;


/**
 * 本类用于数据库操作
 * 1、server打开时创建表
 * 2、用户增删改查
 */

public class OperationOnDatabase {
    public OperationOnDatabase(){}



















    //添加用户
    public boolean addUser(User user) throws IOException {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        try{
            mapper.addUser(user);
            //手动提交事务
            sqlSession.commit();
            sqlSession.close();
            System.out.println("添加成功");
        }catch (Exception e){
            System.out.println("添加失败");
            return false;
        }
        return true;
    }
    //判断用户名是否存在 返回值为boolean
    public boolean checkUsernameExistence(String inputUsername) throws IOException {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        List<User> users = mapper.selectAll();
        boolean checkResult = checkUsernameExistence(inputUsername, users);
        sqlSession.close();
        if(checkResult){
            System.out.println("用户存在");
            return true;
        }else{
            System.out.println("用户不存在");
            return false;
        }
    }
    //修改密码
    public boolean changePassword(String newPassword,String username) throws IOException {
        System.out.println("开始修改密码");
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        try {
            mapper.updatePassword(newPassword,username);
            sqlSession.commit();
            sqlSession.close();
            System.out.println("修改成功");
        }catch (Exception e){
            System.out.println("修改失败");
            return false;
        }
        return true;
    }
    //输入查询的用户名和从数据库中获得的集合
    public boolean checkUsernameExistence(String inputUsername,List<User> users){
        for (User user : users) {
            if(user.getUsername().equals(inputUsername)){
                return true;
            }
        }
        return false;
    }
    public User checkingLoginInformation(String inputUsername,String inputPassword) throws IOException {
        boolean existenceOfInputUsername = this.checkUsernameExistence(inputUsername);
        if(existenceOfInputUsername){
            String resource = "mybatis-config.xml";
            InputStream inputStream = Resources.getResourceAsStream(resource);
            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
            SqlSession sqlSession = sqlSessionFactory.openSession();
            UserMapper mapper = sqlSession.getMapper(UserMapper.class);
            User user = mapper.selectByUsername(inputUsername);
            if(user.getPassword().equals(inputPassword)){
                return user;
            }
        }
        return null;
    }


}
