package com.example.vernyak.service;

import com.example.vernyak.model.Customer;
import com.example.vernyak.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

import java.util.*;
import java.util.stream.Collectors;


@Service
public class CustomerService implements ICustomerService {

    public static Long generateFormattedDate() {
        LocalDateTime now = LocalDateTime.now();

        int hour = now.getHour();
        int day = now.getDayOfMonth();
        int month = now.getMonthValue();
        int year = now.getYear() % 100;
        String formattedHour = String.format("%02d", hour);
        String formattedDay = String.format("%02d", day);
        String formattedMonth = String.format("%02d", month);
        String formattedYear = String.format("%02d", year);

        return Long.valueOf(formattedHour + formattedDay + formattedMonth + formattedYear);
    }

        @Autowired
    private CustomerRepository customerRepository;

    @Override
    public String getCustomer(Customer customer) {

        return null;
    }

    @Override
    public Customer updateCustomer(Customer customer) {
        return null;
    }

    @Override
    public Customer updateCustomer(Long id) {
        return null;
    }

    @Override
    public Customer updateCustomer(Long id, String fullName, String email, String phone) {
        Optional<Customer> customerOptional = customerRepository.findById(String.valueOf(id));

        // Перевіряємо, чи клієнт існує
        if (customerOptional.isPresent()) {
            Customer customer = customerOptional.get();

            if (email.equals(customer.getEmail())) {
                customer.setFullName(fullName);
                customer.setPhone(phone);
                customer.setUpdated(generateFormattedDate());
                return customerRepository.save(customer);
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    @Override
    public Map<String, Object> getCustomer(Long id) {
        Optional<Customer> customerOptional = customerRepository.findById(String.valueOf(id));

        if (customerOptional.isPresent()) {
            Customer customer = customerOptional.get();

            return getStringObjectMap(customer);
        } else {
            return Collections.emptyMap();
        }
    }

    private Map<String, Object> getStringObjectMap(Customer customer) {
        Map<String, Object> result = new HashMap<>();

        if (customer.getIsActive()==Boolean.FALSE){return null;}
        else {
            result.put("id", customer.getId());
            result.put("fullName", customer.getFullName());
            result.put("email", customer.getEmail());
            result.put("phone", customer.getPhone());


            return result;
        }

    }

    @Override
    public List<Map<String, Object>> getAllCustomers() {
        List<Customer> customers = (List<Customer>) customerRepository.findAll();

        return customers.stream().map(customer -> {
            return getStringObjectMap(customer);
        }).collect(Collectors.toList());
    }


    public Customer saveCustomer(Customer customer) {
        customerRepository.save(customer);

        return customer;
    }
    public boolean deleteCustomerMark(Long id) {
        Optional<Customer> customerOptional = customerRepository.findById(String.valueOf(id));
        if (customerOptional.isPresent()) {
            Customer customer = customerOptional.get();
            customer.setIsActive(Boolean.FALSE);
            customer.setUpdated(generateFormattedDate());
            customerRepository.save(customer);
            return true;
        }
        return false;
    }

}
