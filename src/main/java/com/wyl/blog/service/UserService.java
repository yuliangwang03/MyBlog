package com.wyl.blog.service;

import com.wyl.blog.entity.User;

/**
 * @author russe
 */
public interface UserService {
    User checkUser(String username, String password);
}
