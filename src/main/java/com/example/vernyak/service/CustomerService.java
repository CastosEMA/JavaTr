package com.example.vernyak.service;

import com.example.vernyak.repository.CustomerRepository;
import com.example.vernyak.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CustomerService implements ICustomerService{
    @Autowired
    CustomerRepository customerRepository;

    @Override
    public String getCustomer(Customer customer){
    return null;
    }

}
