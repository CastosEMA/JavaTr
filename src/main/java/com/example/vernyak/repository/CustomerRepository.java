package com.example.vernyak.repository;

import com.example.vernyak.model.Customer;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

@Repository
public interface CustomerRepository extends CrudRepository<Customer,String> {
    static List<Customer> findByUsername(String username) {
        return null;
    }

    Customer findById(int id);
    void deleteById(int id);


}
