package com.wyl.blog.dao;

import com.wyl.blog.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author russe
 */
public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsernameAndPassword(String username, String password);
}
