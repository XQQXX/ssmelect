package com.elect.service.impl;

import com.elect.mapper.*;
import com.elect.pojo.*;
import com.elect.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public boolean checkEmail(String email) throws Exception {
        return userDao.findByEmail(email)==null;
    }

    @Override
    public boolean checkCode(String code, String verifyCode) throws Exception{
        return code.equalsIgnoreCase(verifyCode);
    }

    @Override
    public void RegistUser(User user) throws Exception{
        userDao.addUser(user);
    }

    @Override
    public boolean checkEmailCode(String uuid,String emailCode) throws Exception {
        return uuid.equals(emailCode);
    }

    @Override
    public void RegistUserDetil(User user) throws Exception {
        userDao.updateUser(user);
    }


    @Override
    public User checkLogin(User u) throws Exception {
        User user=userDao.findUser(u);
        return user;
    }
}
