package com.wyl.blog.dao;

import com.wyl.blog.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer,Long> {

    Customer findByUsernameAndPassword(String username, String password);
}
