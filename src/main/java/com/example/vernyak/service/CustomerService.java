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

        // Отримуємо компоненти дати і часу
        int hour = now.getHour();
        int day = now.getDayOfMonth();
        int month = now.getMonthValue();
        int year = now.getYear() % 100; // Останні дві цифри року

        // Форматуємо кожен компонент до двозначного числа
        String formattedHour = String.format("%02d", hour);
        String formattedDay = String.format("%02d", day);
        String formattedMonth = String.format("%02d", month);
        String formattedYear = String.format("%02d", year);

        // Складаємо фінальний рядок
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
        // Можливо, треба уточнити реалізацію цього методу або видалити, якщо він не потрібен
        return null;
    }

    @Override
    public Customer updateCustomer(Long id) {
        // Можливо, треба уточнити реалізацію цього методу або видалити, якщо він не потрібен
        return null;
    }

    @Override
    public Customer updateCustomer(Long id, String fullName, String email, String phone) {
        Optional<Customer> customerOptional = customerRepository.findById(String.valueOf(id));
        if (customerOptional.isPresent()) {
            Customer customer = customerOptional.get();
            customer.setFullName(fullName); // Оновлення імені клієнта
            customer.setEmail(email);       // Оновлення email
            customer.setPhone(phone);       // Оновлення телефону
            customer.setUpdated(generateFormattedDate());

            // Збереження зміненого клієнта в базі даних
            return customerRepository.save(customer);
        } else {
            // Можна додати логіку обробки випадку, коли клієнт не знайдений
            return null;
        }
    }

    @Override
    public Map<String, Object> getCustomer(Long id) {
        // Знаходимо клієнта за ID
        Optional<Customer> customerOptional = customerRepository.findById(String.valueOf(id));

        if (customerOptional.isPresent()) {
            Customer customer = customerOptional.get();

            // Створюємо мапу з потрібними полями
            return getStringObjectMap(customer);
        } else {
            // Повертаємо порожню мапу або кидаємо виняток, якщо клієнт не знайдений
            return Collections.emptyMap();
            // Або можна кинути виняток, наприклад: throw new CustomerNotFoundException("Клієнт не знайдений");
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
        // Отримуємо всіх клієнтів
        List<Customer> customers = (List<Customer>) customerRepository.findAll();

        // Перетворюємо список клієнтів на список мап, що містять лише потрібні поля
        return customers.stream().map(customer -> {
            return getStringObjectMap(customer);
        }).collect(Collectors.toList());
    }


    public void saveCustomer(Customer customer) {
        customerRepository.save(customer);

    }
}
