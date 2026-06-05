package com.bankproject.demo.service;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    public Customer getCustomer(String id) {
        Optional<Customer> c = repository.findById(id);
        return c.orElse(null);
    }

    public Customer getCustomerByName(String name) {
        //Optional<Customer> customer = repository.findByName(name);
        return null;//customer.orElse(null);
    }

    public List<Customer> getPremiumCustomers() {
        return new ArrayList<>(); //repository.findPremiumCustomers();
    }

    public List<Account> getAllAccounts() {
        List<Customer> customers = repository.findAll();
        List<Account> accounts = new ArrayList<>();
        for (Customer c : customers) {
            if (c.getAccounts() != null) {
                accounts.addAll(c.getAccounts());
            }
        }
        return accounts;
    }

    public Account getAccount(String id) {
        List<Account> accounts = getAllAccounts();
        for (Account a : accounts) {
            if (a.getId().equals(id)) {
                return a;
            }
        }
        return null;
    }

    public List<Account> getAccountByName(String name) {
        Customer c = getCustomerByName(name);
        if (c == null || c.getAccounts() == null || c.getAccounts().isEmpty()) {
            return null;
        }
        return c.getAccounts();
    }    

    public Customer CreateCustomer() {
        Customer c = new Customer(null, "Default Name", "Default Email", new ArrayList<>());
        return repository.save(c);
    }

    public Customer CreateCustomer(String name, String email) {
        Customer c = new Customer(null, name, email, new ArrayList<>());
        return repository.save(c);
    }

    public Customer UpdateCustomer(String id, Customer c) {
        Optional<Customer> existing = repository.findById(id);
        if (existing.isPresent()) {
            Customer customer = existing.get();
            customer.setName(c.getName());
            customer.setEmail(c.getEmail());
            customer.setAccounts(c.getAccounts());
            return repository.save(customer);
        }
        throw new IllegalArgumentException("Customer with ID " + id + " does not exist");
    }

    public void deleteCustomer(String id) {
        repository.deleteById(id);
    }

    public Account CreateAccount(String customerId) {
        Optional<Customer> customer = repository.findById(customerId);
        if (customer.isPresent()) {
            Customer c = customer.get();
            Account a = new Account(null, "Default Account Number", "Checking", 0.0);
            List<Account> accounts = c.getAccounts();
            if (accounts == null) {
                accounts = new ArrayList<>();
                c.setAccounts(accounts);
            }
            accounts.add(a);
            repository.save(c);
            return a;
        }
        throw new IllegalArgumentException("Customer with ID " + customerId + " does not exist");
    }

    public void UpdateAccount(String accountId, Account account) {
        List<Customer> customers = repository.findAll();
        for (Customer c : customers) {
            if (c.getAccounts() != null) {
                for (Account acc : c.getAccounts()) {
                    if (acc.getId().equals(accountId)) {
                        acc.setAccountNumber(account.getAccountNumber());
                        acc.setAccountType(account.getAccountType());
                        acc.setBalance(account.getBalance());
                        repository.save(c);
                        return;
                    }
                }
            }
        }
        throw new IllegalArgumentException("Account with ID " + accountId + " does not exist");
    }

    public void DeleteAccount(String accountId) {
        List<Customer> customers = repository.findAll();
        for (Customer c : customers) {
            if (c.getAccounts() != null) {
                boolean removed = c.getAccounts().removeIf(a -> a.getId().equals(accountId));
                if (removed) {
                    repository.save(c);
                    return;
                }
            }
        }
        throw new IllegalArgumentException("Account with ID " + accountId + " does not exist");
    }
}