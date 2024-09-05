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
    public ResponseEntity<String> createCustomer(
            @RequestParam(value = "fullName") String fullName,
            @RequestParam(value = "email") String email,
            @RequestParam(value = "phone", required = false) String phone) {

        // Перевірка правильності email
        if (email == null || !email.contains("@") || email.length() < 2 || email.length() > 100) {
            return new ResponseEntity<>("Invalid email format", HttpStatus.BAD_REQUEST);
        }

        // Перевірка довжини fullName
        if (fullName == null || fullName.length() < 2 || fullName.length() > 50) {
            return new ResponseEntity<>("Invalid fullName length", HttpStatus.BAD_REQUEST);
        }

        // Перевірка формату phone
        if (phone != null && (phone.length() < 6 || phone.length() > 14 || !phone.startsWith("+") || !phone.matches("\\+\\d+"))) {
            return new ResponseEntity<>("Invalid phone format", HttpStatus.BAD_REQUEST);
        }

        // Створюємо новий об'єкт Customer
        Customer customer = new Customer();
        customer.setFullName(fullName);
        customer.setEmail(email);
        customer.setPhone(phone);

        // Встановлюємо дату створення та оновлення
        String formattedDate = String.valueOf(CustomerService.generateFormattedDate());
        try {
            customer.setCreated(Long.valueOf(formattedDate));
            customer.setUpdated(Long.valueOf(formattedDate));
        } catch (NumberFormatException e) {
            return new ResponseEntity<>("Date formatting error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        customer.setIsActive(true);

        // Зберігаємо клієнта в базі даних через сервіс
        customerService.saveCustomer(customer);

        // Повертаємо успішну відповідь
        return new ResponseEntity<>("Customer created", HttpStatus.CREATED);
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
