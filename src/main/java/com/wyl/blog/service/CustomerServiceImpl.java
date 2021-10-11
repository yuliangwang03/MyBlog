package com.wyl.blog.service;

import com.wyl.blog.dao.CustomerRepository;
import com.wyl.blog.entity.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl implements CustomerService{

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public Customer checkCustomer(String username, String password) {
        return customerRepository.findByUsernameAndPassword(username, password);
    }

    @Override
    public Customer addCustomer(Customer customer) {
        return customerRepository.save(customer);
    }
}
