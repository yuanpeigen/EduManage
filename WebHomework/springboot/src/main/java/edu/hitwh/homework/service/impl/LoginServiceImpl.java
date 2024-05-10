package edu.hitwh.homework.service.impl;

import edu.hitwh.homework.mapper.LoginMapper;
import edu.hitwh.homework.pojo.User;
import edu.hitwh.homework.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private LoginMapper loginMapper;


    @Override
    public User login(User user) {
        return loginMapper.login(user);
    }

    @Override
    public void register(User user) {

        loginMapper.register(user);
    }
}
