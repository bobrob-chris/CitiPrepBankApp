package com.bankproject.demo.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "accounts")
public class Account {
    @Id
    private String id;
    private String accountNumber;
    private String accountType;
    private double balance;

    public Account(String id, String accountNumber, String accountType, double balance) {
        this.id = id;
        this.accountNumber = accountNumber;
        this.accountType = accountType;
        this.balance = balance;
    }
    public String getId() {return id;}
    public String getAccountNumber() {return accountNumber;}
    public String getAccountType() {return accountType;}
    public double getBalance() {return balance;}

    public void setId(String id) {this.id = id;}
    public void setAccountNumber(String accountNumber) {this.accountNumber = accountNumber;}
    public void setAccountType(String accountType) {this.accountType = accountType;}
    public void setBalance(double balance) {this.balance = balance;}
    
}
