package com.bankproject.demo.model;

public class Account {
    private Long id;
    private String accountNumber;
    private String accountType;
    private double balance;

    public Account(Long id, String accountNumber, String accountType, double balance) {
        this.id = id;
        this.accountNumber = accountNumber;
        this.accountType = accountType;
        this.balance = balance;
    }
    public Long getId() {return id;}
    public String getAccountNumber() {return accountNumber;}
    public String getAccountType() {return accountType;}
    public double getBalance() {return balance;}

    public void setId(Long id) {this.id = id;}
    public void setAccountNumber(String accountNumber) {this.accountNumber = accountNumber;}
    public void setAccountType(String accountType) {this.accountType = accountType;}
    public void setBalance(double balance) {this.balance = balance;}
    
}
