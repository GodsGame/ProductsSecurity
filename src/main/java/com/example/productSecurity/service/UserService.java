package com.example.productSecurity.service;

import com.example.productSecurity.pojo.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    /**
     * 判断邮箱和密码是否匹配
     * @param email
     * @param password
     * @return
     */
    boolean isMatch(String email, String password);

    /**
     * 判断用户是否存在
     * @param email
     * @return
     */
    boolean isExist(String email);

    /**
     * 通过用户名获取用户id
     * @param username
     * @return
     */
    Long getIdByUsername(String username);

    /**
     * 通过邮箱获取用户id
     * @param email
     * @return
     */
    Long getIdByEmail(String email);

    /**
     * 用户注册
     * @param user
     */
    void registered(User user);
}
