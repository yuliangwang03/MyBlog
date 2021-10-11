package com.wyl.blog.service;

import com.wyl.blog.entity.Customer;

public interface CustomerService {

    Customer checkCustomer(String username,String password);

    Customer addCustomer(Customer customer);
}
