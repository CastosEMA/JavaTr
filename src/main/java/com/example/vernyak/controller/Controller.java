package com.example.vernyak.controller;

import com.example.vernyak.service.CustomerService;
import com.example.vernyak.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class Controller {

    private final CustomerService customerService;

    @Autowired
    public Controller(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("/customer")
    public String createCustomer() {
        // Логіка для створення клієнта
        return "Customer created";
    }

    @GetMapping("/customer")
    public Object getCustomers(@RequestParam(value = "id", required = false) Long id) {
        if (id != null) {
            // Якщо id є в параметрах, виконуємо пошук одного клієнта
            return customerService.getCustomer(id);
        } else {
            // Якщо id немає, виконуємо пошук всіх клієнтів
            return customerService.getAllCustomers();
        }
    }
    @PutMapping("/customer")
    public String updateCustomer() {
        // Логіка для оновлення клієнта
        return "Customer updated";
    }

    @DeleteMapping("/customer/{id}")
    public String deleteCustomer(@PathVariable Long id) {
        // Логіка для видалення клієнта
        return "Customer deleted";
    }
}
