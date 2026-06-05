package com.bankproject.demo.controller;


import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bankproject.demo.model.Account;
import com.bankproject.demo.model.Customer;
import com.bankproject.demo.service.CustomerService;

@CrossOrigin(origins = "http://localhost:5173")

@RestController
@RequestMapping("/api")
public class CustomerController {

    private final CustomerService service;

    public CustomerController(CustomerService service) {
        this.service = service;
    }

    @GetMapping("/customers")
    public List<Customer> getAllCustomers() {
        return service.getAllCustomers();
    }

    @GetMapping("/customers/{id}")
    public Customer getCustomer(@PathVariable Long id) {
        return service.getCustomer(id);
    }

    @GetMapping("/customers/search?name={name}")
    public Customer getCustomerByName(@PathVariable String name) {
        return service.getCustomerByName(name);
    }

    @GetMapping("/customers/premium")
    public List<Customer> getPremiumCustomers() {
        return service.getPremiumCustomers();
    }

    
    @GetMapping("/accounts")
    public List<Account> getAllAccounts() {
        return service.getAllAccounts();
    }

    

    @GetMapping("/accounts/search?name={name}")
    public List<Account> getAccountByName(@PathVariable String name) {
        return service.getAccountByName(name);
    }

    @GetMapping("/accounts/{id}")
    public Account getAccount(@PathVariable Long id) {
        return service.getAccount(id);
    }
        
    
    @PostMapping("/customers")
    public Customer createCustomer() {
        return service.CreateCustomer(); //should return id
    }
    
    
    @PutMapping("/customers/{id}")
    public Customer updateCustomer(
            @PathVariable Long id,
            @RequestBody Customer customer) {

        return service.UpdateCustomer(id, customer);
    }

    
    @DeleteMapping("/customers/{id}")
    public void deleteCustomer(@PathVariable Long id) {
        service.deleteCustomer(id);
    }

    @PostMapping("/accounts")
    public Account createAccount(@RequestBody Long customerId) {
        return service.CreateAccount(customerId);
    }

    @PutMapping("/accounts/{id}")
    public void updateAccount(
            @PathVariable Long id,
            @RequestBody Account account) {
        // Implement account update logic here
        service.UpdateAccount(id, account);
    }

    @DeleteMapping("/accounts/{id}")
    public void deleteAccount(@PathVariable Long id) {
        // Implement account deletion logic here
        service.DeleteAccount(id);
    }
    
}