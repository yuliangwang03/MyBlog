package com.wyl.blog.service;

import com.wyl.blog.dao.UserRepository;
import com.wyl.blog.entity.User;
import com.wyl.blog.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author russe
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User checkUser(String username, String password) {

        return userRepository.findByUsernameAndPassword(username, MD5Util.code(password));
    }
}
