package com.example.vernyak.controller;

import com.example.vernyak.service.CustomerService;
import com.example.vernyak.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Customer> createCustomer(
            @RequestParam(value = "fullName") String fullName,
            @RequestParam(value = "email") String email,
            @RequestParam(value = "phone") String phone) {

        // Перевірка fullName
        if (fullName == null || fullName.trim().isEmpty() || fullName.length() < 2 || fullName.length() > 50) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        // Перевірка email
        if (email == null || email.length() < 2 || email.length() > 100 || !email.contains("@")) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        // Перевірка phone (опціонально)
        if (phone != null && (phone.length() < 6 || phone.length() > 14 || !phone.startsWith("+") || !phone.matches("\\+\\d+"))) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        // Створюємо новий об'єкт Customer
        Customer customer = new Customer();
        customer.setFullName(fullName.trim()); // Видаляємо пробіли на початку і в кінці
        customer.setEmail(email);
        customer.setPhone(phone != null ? phone : "");

        // Встановлюємо дату створення та оновлення
        String formattedDate = String.valueOf(CustomerService.generateFormattedDate());
        customer.setCreated(Long.valueOf(formattedDate));
        customer.setUpdated(Long.valueOf(formattedDate));
        customer.setIsActive(Boolean.TRUE);

        // Зберігаємо клієнта в базі даних через сервіс
        Customer savedCustomer = customerService.saveCustomer(customer);

        // Повертаємо успішну відповідь з даними про створеного клієнта
        return new ResponseEntity<>(savedCustomer, HttpStatus.CREATED);
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
