package com.example.productSecurity.service;

import com.example.productSecurity.dao.UserMapper;
import com.example.productSecurity.pojo.User;
import com.example.productSecurity.utils.MybatisUtils;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{
    /**
     * 判断用户名和密码是否匹配
     * @param email
     * @param password
     * @return
     */
    @Override
    public boolean isMatch(String email, String password) {
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        if (password.equals(userMapper.getPassword(email))) {
            sqlSession.close();
            return true;
        } else {
            sqlSession.close();
            return false;
        }
    }

    /**
     * 判断用户名是否存在
     * @param email
     * @return
     */
    @Override
    public boolean isExist(String email) {
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        if (!(userMapper.isExist(email) == null)) {
            sqlSession.close();
            return true;
        } else {
            sqlSession.close();
            return false;
        }
    }

    /**
     * 通过用户名获取用户id
     * @param username
     * @return
     */
    @Override
    public Long getIdByUsername(String username) {
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        Long id = userMapper.getIdByUsername(username);
        sqlSession.close();
        return id;
    }

    /**
     * 通过邮箱获取用户id
     * @param email
     * @return
     */
    @Override
    public Long getIdByEmail(String email) {
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        Long id = userMapper.getIdByEmail(email);
        sqlSession.close();
        return id;
    }

    /**
     * 用户注册
     * @param user
     */
    @Override
    public void registered(User user) {
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        userMapper.addUser(user);
        sqlSession.commit();
        sqlSession.close();
    }
}
