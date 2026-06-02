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

    public void CreateCustomer(String name, String email) {
        repository.AddCustomer(name, email);
    }

    public void UpdateCustomer(Long id, Customer c) {
        repository.UpdateCustomer(id, c);
    }
    /*
    public Customer createCustomer(Customer customer) {
        return repository.save(customer);
    }

    public Customer updateCustomer(int id, Customer customer) {
        customer.setId(id);
        return repository.save(customer);
    }

    public void deleteCustomer(int id) {
        repository.delete(id);
    }
        */
}