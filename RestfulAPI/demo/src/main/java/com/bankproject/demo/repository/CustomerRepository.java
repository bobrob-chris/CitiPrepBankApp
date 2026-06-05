package com.bankproject.demo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.bankproject.demo.model.Customer;

@Repository
public interface CustomerRepository extends MongoRepository<Customer, String> {

    /*
    /**
     * Find a customer by name

    Optional<Customer> findByName(String name);

    /**
     * Find all customers by email
     
    Optional<Customer> findByEmail(String email);

    /**
     * Custom query to find premium customers (those with accounts)
    
    @Query("{ 'accounts': { $exists: true, $ne: [] } }")
    List<Customer> findPremiumCustomers();

   Find customers with at least one account
     
    @Query("{ 'accounts': { $exists: true, $ne: [] } }")
    List<Customer> findCustomersWithAccounts();
    */
}