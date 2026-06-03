package com.bankproject.demo;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import com.bankproject.demo.service.CustomerService;
import com.bankproject.demo.repository.CustomerRepository;
import com.bankproject.demo.model.Customer;
import com.bankproject.demo.model.Account;
import java.util.*;


@SpringBootTest
public class ServiceTest {
    CustomerRepository repository = new CustomerRepository();
    CustomerService service = new CustomerService(repository);

    // ==================== getAllCustomers Tests ====================
    @Test
    public void testGetAllCustomersPositive() {
        // Should return all 3 customers from repository
        assertEquals(3, service.getAllCustomers().size());
    }

    @Test
    public void testGetAllCustomersNegative() {
        // Should NOT return only 2 customers
        assertNotEquals(2, service.getAllCustomers().size());
    }

    // ==================== getCustomer Tests ====================
    @Test
    public void testGetCustomerPositive() {
        // Should return customer with id 0 (John Doe)
        Customer customer = service.getCustomer(0L);
        assertNotNull(customer);
        assertEquals("John Doe", customer.getName());
    }

    @Test
    public void testGetCustomerNegative() {
        // Should return null for non-existent id
        Customer customer = service.getCustomer(999L);
        assertNull(customer);
    }

    // ==================== getCustomerByName Tests ====================
    @Test
    public void testGetCustomerByNamePositive() {
        // Should find customer by exact name
        Customer customer = service.getCustomerByName("Jane Smith");
        assertNotNull(customer);
        assertEquals("jane@yahoo.com", customer.getEmail());
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
        // Should return customers with total balance > 2000
        // Jane Smith has 5000, Andy Brown has 2000 (not > 2000)
        // So only Jane Smith should be premium
        List<Customer> premiumCustomers = service.getPremiumCustomers();
        assertNotNull(premiumCustomers);
        assertTrue(premiumCustomers.size() > 0);
        assertTrue(premiumCustomers.stream().anyMatch(c -> c.getName().equals("Jane Smith")));
    }

    @Test
    public void testGetPremiumCustomersNegative() {
        // Should NOT include customers with low balance
        var premiumCustomers = service.getPremiumCustomers();
        // John Doe has only 1000, should not be premium
        assertFalse(premiumCustomers.stream().anyMatch(c -> c.getName().equals("John Doe")));
    }

    // ==================== getAllAccounts Tests ====================
    @Test
    public void testGetAllAccountsPositive() {
        // Should return all 3 accounts from repository
        var accounts = service.getAllAccounts();
        assertEquals(3, accounts.size());
    }

    @Test
    public void testGetAllAccountsNegative() {
        // Should NOT return only 2 accounts
        List<Account> accounts = service.getAllAccounts();
        assertNotNull(accounts);
        assertNotEquals(2, accounts.size());
    }

    // ==================== getAccount Tests ====================
    @Test
    public void testGetAccountPositive() {
        // Should find account by id
        List<Account> accounts = service.getAllAccounts();
        Long existingAccountId = accounts.get(0).getId();
        Account account = service.getAccount(existingAccountId);
        assertNotNull(account);
        assertEquals(existingAccountId, account.getId());
    }

    @Test
    public void testGetAccountNegative() {
        // Should return null for non-existent account id
        Account account = service.getAccount(1L);
        assertNull(account);
    }

    // ==================== getAccountByName Tests ====================
    @Test
    public void testGetAccountByNamePositive() {
        // Should return accounts for existing customer
        List<Account> accounts = service.getAccountByName("John Doe");
        assertNotNull(accounts);
        assertEquals(1, accounts.size());
        assertEquals("123456789", accounts.get(0).getAccountNumber());

        List<Account> accounts2 = service.getAccountByName("Jane Smith");
        assertNotNull(accounts2);
        assertEquals(1, accounts2.size());
        assertEquals("987654321", accounts2.get(0).getAccountNumber());
    }

    @Test
    public void testGetAccountByNameNegative() {
        // Should return null for non-existent customer name
        List<Account> accounts = service.getAccountByName("NonExistent Person");
        assertNull(accounts);
    }
}
