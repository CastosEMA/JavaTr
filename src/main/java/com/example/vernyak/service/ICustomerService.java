package com.example.vernyak.service;

import com.example.vernyak.model.Customer;

import java.util.List;
import java.util.Map;

public interface ICustomerService {
    String getCustomer(Customer customer);

    Customer updateCustomer(Customer customer);

    Customer updateCustomer(Long id);

    Customer updateCustomer(Long id, String fullName, String email, String phone);

    Map<String, Object> getCustomer(Long id);

    List<Map<String, Object>> getAllCustomers();
}
