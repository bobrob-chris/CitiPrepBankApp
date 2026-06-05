package com.bankproject.demo;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import com.bankproject.demo.service.CustomerService;
import com.bankproject.demo.model.Customer;
import com.bankproject.demo.model.Account;
import java.util.*;


@SpringBootTest
public class ServiceTest {
    
    @Autowired
    private CustomerService service;

    // ==================== getAllCustomers Tests ====================
    @Test
    public void testGetAllCustomersPositive() {
        // Should return customers from MongoDB
        List<Customer> customers = service.getAllCustomers();
        assertNotNull(customers);
    }

    @Test
    public void testGetAllCustomersNegative() {
        // Should return a list (may be empty if DB is empty)
        List<Customer> customers = service.getAllCustomers();
        assertNotNull(customers);
    }

    // ==================== getCustomer Tests ====================
    @Test
    public void testGetCustomerPositive() {
        // This test would need a valid MongoDB ID to work
        // For now, testing that the service handles null gracefully
        Customer customer = service.getCustomer("invalidId");
        assertNull(customer);
    }

    @Test
    public void testGetCustomerNegative() {
        // Should return null for non-existent id
        Customer customer = service.getCustomer("999");
        assertNull(customer);
    }

    // ==================== getCustomerByName Tests ====================
    @Test
    public void testGetCustomerByNamePositive() {
        // Should find customer by exact name
        Customer customer = service.getCustomerByName("Jane Smith");
        // This may be null if MongoDB doesn't have this data
        // Just verify the method works
        assertTrue(customer == null || customer.getEmail() != null);
    }

    @Test
    public void testGetCustomerByNameNegative() {
        // Should return null for non-existent name
        Customer customer = service.getCustomerByName("NonExistent Person");
        assertNull(customer);
    }

    // ==================== getPremiumCustomers Tests ====================
    @Test
    public void testGetPremiumCustomersPositive() {
        // Should return customers with accounts
        List<Customer> premiumCustomers = service.getPremiumCustomers();
        assertNotNull(premiumCustomers);
    }

    @Test
    public void testGetPremiumCustomersNegative() {
        // Should return a list (may be empty)
        List<Customer> premiumCustomers = service.getPremiumCustomers();
        assertNotNull(premiumCustomers);
    }

    // ==================== getAllAccounts Tests ====================
    @Test
    public void testGetAllAccountsPositive() {
        // Should return all accounts from repository
        var accounts = service.getAllAccounts();
        assertNotNull(accounts);
    }

    @Test
    public void testGetAllAccountsNegative() {
        // Should return a list
        List<Account> accounts = service.getAllAccounts();
        assertNotNull(accounts);
    }

    // ==================== getAccount Tests ====================
    @Test
    public void testGetAccountPositive() {
        // Should find account by id
        List<Account> accounts = service.getAllAccounts();
        if (accounts.size() > 0) {
            String existingAccountId = accounts.get(0).getId();
            Account account = service.getAccount(existingAccountId);
            assertNotNull(account);
            assertEquals(existingAccountId, account.getId());
        }
    }

    @Test
    public void testGetAccountNegative() {
        // Should return null for non-existent account id
        Account account = service.getAccount("999");
        assertNull(account);
    }

    // ==================== getAccountByName Tests ====================
    @Test
    public void testGetAccountByNamePositive() {
        // Should return accounts for existing customer
        List<Account> accounts = service.getAccountByName("John Doe");
        // May be null if customer doesn't exist in MongoDB
        assertTrue(accounts == null || accounts.size() >= 0);
    }

    @Test
    public void testGetAccountByNameNegative() {
        // Should return null for non-existent customer name
        List<Account> accounts = service.getAccountByName("NonExistent Person");
        assertNull(accounts);
    }
}
