package com.example.vernyak.controller;

import com.example.vernyak.service.CustomerService;
import com.example.vernyak.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

        if (fullName == null || fullName.trim().isEmpty() || fullName.length() < 2 || fullName.length() > 50) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if (email == null || email.length() < 2 || email.length() > 100 || !email.contains("@")) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if (phone != null && (phone.length() < 6 || phone.length() > 14 || !phone.startsWith("+") || !phone.matches("\\+\\d+"))) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Customer customer = new Customer();
        customer.setFullName(fullName.trim());
        customer.setEmail(email);
        customer.setPhone(phone != null ? phone : "");

        String formattedDate = String.valueOf(CustomerService.generateFormattedDate());
        customer.setCreated(Long.valueOf(formattedDate));
        customer.setUpdated(Long.valueOf(formattedDate));
        customer.setIsActive(Boolean.TRUE);

        Customer savedCustomer = customerService.saveCustomer(customer);

        return new ResponseEntity<>(savedCustomer, HttpStatus.CREATED);
    }



    @GetMapping("/customer")
    public Object getCustomers(@RequestParam(value = "id", required = false) Long id) {
        if (id != null) {
            return customerService.getCustomer(id);
        } else {
            return customerService.getAllCustomers();
        }
    }
    @PutMapping("/customer/{id}")
    public ResponseEntity<Customer> updateCustomer(
            @PathVariable Long id,
            @RequestParam(value = "fullName") String fullName,
            @RequestParam(value = "email") String email,
            @RequestParam(value = "phone") String phone) {

        Customer updatedCustomer = customerService.updateCustomer(id, fullName, email, phone);

        if (updatedCustomer != null) {
            return new ResponseEntity<>(updatedCustomer, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @DeleteMapping("/customer/{id}")
    public ResponseEntity<String> deleteCustomer(@PathVariable Long id) {
        boolean isDeleted = customerService.deleteCustomerMark(id);

        if (isDeleted) {
            return new ResponseEntity<>("Customer is deleted.", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Customer not found.", HttpStatus.NOT_FOUND);
        }
    }

}
