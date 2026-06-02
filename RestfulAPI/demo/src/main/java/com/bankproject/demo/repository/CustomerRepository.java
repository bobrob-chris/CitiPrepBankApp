package com.bankproject.demo.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Repository;

import com.bankproject.demo.model.Account;
import com.bankproject.demo.model.Customer;

@Repository
public class CustomerRepository {

    ///private final CustomerController customerController;
    //To make searching methods faster, I'd make dual maps mapping id to customers and name to customers
    //But I don't think that's necessary right now;
    private List<Customer> customers = new ArrayList<>();
    private List<Account> accounts = new ArrayList<>();

    static Long counter = 0L; 

    public CustomerRepository() {
        List<Account> johnAccounts = new ArrayList<>();
        List<Account> janeAccounts = new ArrayList<>();
        List<Account> andyAccounts = new ArrayList<>();


        customers.add(new Customer(counter++,"John Doe", "john@outlook.com",johnAccounts));
        customers.add(new Customer(counter++,"Jane Smith", "jane@yahoo.com",janeAccounts));
        customers.add(new Customer(counter++,"Andy Brown", "andy@gmail.com",andyAccounts));

        Account a1 = new Account(counter++,"123456789","Checking",1000.00);
        johnAccounts.add(a1); accounts.add(a1);
        Account a2 = new Account(counter++,"230234543", "Saving", 5000);
        janeAccounts.add(a2); accounts.add(a2);
        Account a3 = new Account(counter++,"345678901", "Checking", 2000.00);
        andyAccounts.add(a3); accounts.add(a3);
        //this.customerController = customerController;
    }

    public List<Customer> findAll() {
        return new ArrayList<>(customers);
    }

    public Customer findById(Long id){
        for (Customer c : customers) {
            if (Objects.equals(c.getId(), id)) {
                return c;
            }
        }
        return null;
    }

    public List<Account> findAllAccounts() {
        return new ArrayList<>(accounts);
    }

    public Customer AddCustomer(String name, String email) {
        Customer c = new Customer(counter++, name, email, new ArrayList<>());
        customers.add(c);
        return c;
    }

    public void AddCustomer(Customer c) {
        System.out.println("____HITME_____");
        Long id = c.getId();
        if (id == null || findById(id) != null) {
            //throw new IllegalArgumentException("Customer with the same ID already exists or ID is null");
            id = counter++;
            c.setId(id);
        }
        customers.add(c);
        List<Account> accs = c.getAccounts();
        accounts.addAll(accs);
    }

    public void UpdateCustomer(Long id, Customer c) {
        Customer existing = findById(id);
        if (existing == null) {
            throw new IllegalArgumentException("Customer with ID " + id + " does not exist");
        }
        existing.setName(c.getName());
        existing.setEmail(c.getEmail());
        existing.setAccounts(c.getAccounts());
    }
}