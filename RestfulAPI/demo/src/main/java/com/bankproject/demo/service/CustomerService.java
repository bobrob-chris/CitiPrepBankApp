package com.bankproject.demo.service;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Service;

import com.bankproject.demo.model.Account;
import com.bankproject.demo.model.Customer;
import com.bankproject.demo.repository.CustomerRepository;

@Service
public class CustomerService {
    private final CustomerRepository repository;

    public CustomerService(CustomerRepository repository) {
        this.repository = repository;
    }

    public List<Customer> getAllCustomers() {
        return repository.findAll();
    }

    public Customer getCustomer(Long id) {
        Customer c = repository.findById(id);
        /*
        if (c == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
            */
        return c;
    }

    public Customer getCustomerByName(String name) {
        List<Customer> customers = repository.findAll();
        for (Customer c : customers) {
            if (c.getName().equals(name)) {
                return c;
            }
        }
        return null;
    }

    public List<Customer> getPremiumCustomers() {
        List<Customer> customers = repository.findAll();
        List<Customer> premiumCustomers = new ArrayList<>();
        for (Customer c : customers) {
            List<Account> accounts = c.getAccounts();
            double balanceSum = 0;
            for (Account a : accounts) {
                balanceSum += a.getBalance();
            }
            if (balanceSum > 2000) {
                premiumCustomers.add(c);
            }
        }
        return premiumCustomers;
    }

    public List<Account> getAllAccounts() {
        return repository.findAllAccounts();
    }

    public Account getAccount(Long id) {
        List<Account> accounts = repository.findAllAccounts();
        for (Account a : accounts) {
            if (Objects.equals(a.getId(), id)) {
                return a;
            }
        }
        return null;
    }

    public List<Account> getAccountByName(String name) {
        Customer c = getCustomerByName(name);
        if (c == null) {
            return null;
        }
        return c.getAccounts().isEmpty() ? null : c.getAccounts();
    }    

    public Customer CreateCustomer(String name, String email) {
        return repository.AddCustomer(name, email);
    }
    public Customer CreateCustomer(){
        return repository.AddCustomer();
    }

    public Customer UpdateCustomer(Long id, Customer c) {
        return repository.UpdateCustomer(id, c);
    }
    public void deleteCustomer(Long id) {
        repository.deleteCustomer(id);
    }

    public Account CreateAccount(Long customerId) {
        return repository.AddAccount(customerId);
    }

    public void UpdateAccount(Long accountId, Account account) {
        repository.UpdateAccount(accountId, account);
    }

    public void DeleteAccount(Long accountId) {
        repository.DeleteAccount(accountId);
    }
}