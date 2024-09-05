package com.example.vernyak.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class Controller {

    @PostMapping("/customer")
    public String createCustomer() {
        return "ваоів";
    }
    @GetMapping("/customer/{id}")
    public String getCustomerById(@PathVariable Long id) {
        // Виконайте необхідні дії з отриманим id, наприклад, знайдіть клієнта в базі даних.
        return "ваоів для id: " + id;
    }

    @GetMapping("/customer")
    public String getOneCustomer() {
        return "ваоів";
    }
    @PutMapping("/customer")
    public String updateCustomer() {
        return "ваоів";
    }
    @DeleteMapping("/customer")
    public String deleteCustomer() {
        return "ваоів";
    }

}
