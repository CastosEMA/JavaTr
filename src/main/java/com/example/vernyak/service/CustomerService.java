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
        List<Customer> cus = CustomerRepository.findByUsername(customer.getUserName());
        System.out.println(cus.size());
        if (cus != null || cus.size()>0){
            if (cus.get(0).getPassword().equals(customer.getPassword())){
                return "Succes";
            }
        }
        return "Failed";
    }

}
