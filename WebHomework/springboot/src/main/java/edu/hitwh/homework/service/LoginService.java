package edu.hitwh.homework.service;

import edu.hitwh.homework.pojo.User;

public interface LoginService {

    /**
     * 获取登录信息
     * @param user
     * @return
     */
    User login(User user);

    /**
     * 注册
     * @param userID
     * @param password
     */
    void register(User user);
}
