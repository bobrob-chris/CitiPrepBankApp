/*
BankApp
Main -> Welcome Message -> Authentication
2 stakeholders - client, customer

2 dashboards - adminMenu, customerMenu

methods - add (account), delete, update view all
programming and ocd concepts


 */

import java.util.*;



public class Main{
    static Scanner sc = new Scanner(System.in);
    static List<Customer> customers = new ArrayList<>();
    static List<User> users = new ArrayList<>();
    static int counter = 1;





    public static void main(String[] args){
        //State init
        PopulateCustomers();
        AddAdmin();

        //App Start;
        PrintWelcomeMessage();
        User currentUser = checkValidUser();
        while (currentUser == null) {
            System.out.println("Please try again...");
            currentUser = checkValidUser();
        }

        if (currentUser instanceof Admin){
            System.out.println("Welcome Admin");
            LaunchAdminMenu();
            //Admin Menu
        }
        else if (currentUser instanceof Customer){
            System.out.println("Welcome " + currentUser.getUsername());
            LaunchCustomerMenu();
            //Customer Menu
        }

    }

    private static void LaunchAdminMenu(){
        do {
            System.out.println("\n\n____Admin Menu____");
            System.out.println("Please choose from one of the numbered options below:");
            System.out.println("1) Create an account");
            System.out.println("2) View all accounts");
            System.out.println("3) Deposit");
            System.out.println("4) Withdraw"); 
            System.out.println("5) Transfer");
            System.out.println("6) Close Account");
            System.out.println("7) Exit");


            String response = sc.nextLine();
            response = response.trim();
            int index = Integer.parseInt(response);

            switch(index){
                case 1:
                    CreateAccount();
                    break;
                case 2:
                    ViewAllAccounts();
                    break;
                case 3:
                    Deposit();
                    break;

                case 4:
                    Withdraw();
                    break;
                case 5:
                    Transfer();
                    break;
                case 6:
                    System.out.println("UNDER CONSTRUCTION");
                    break;
                case 7:
                    return;

            }
        }
        while (true);
    }

    //Ask the user for what number of account they'd like to access first.
    private static Account findAccount(Customer accountHolder){
        Account account = null;
        String accountNumber = sc.nextLine();
        accountNumber = accountNumber.trim();
        List<Account> accounts = accountHolder.getAccounts();
        for (Account a : accounts){
            if (a.getAccountNumber().equals(accountNumber)){
                account = a;
            }
        }
        return account;
    }

    private static void Transfer(){
        Customer sourceAccountHolder = getCustomer();
        System.out.println("Please enter the account number you would like to withdraw from: ");
        Account sourceAccount = findAccount(sourceAccountHolder);
        
        Customer destinationAccountHolder = getCustomer();
        System.out.println("Please enter the account number you would like to deposit into: ");
        Account destinationAccount = findAccount(destinationAccountHolder);
    

        System.out.println("Please enter the amount you would like to transfer: ");
        String amountString = sc.nextLine();
        double amount = Double.parseDouble(amountString);

        sourceAccount.Withdraw(amount);
        destinationAccount.Deposit(amount);

        sourceAccount.PrintReceipt();
        destinationAccount.PrintReceipt();
    }

    private static void Deposit(){
        Customer accountHolder = getCustomer();
        System.out.println("Please enter the account number you would like to deposit into: ");
        Account account = findAccount(accountHolder);
        if (account == null){
            System.out.println("Unable to find account with that account number, please try a different account number or customer.");
            return;
        }
        System.out.println("Please enter the amount you would like to deposit: ");
        String amountString = sc.nextLine();
        double amount = Double.parseDouble(amountString);
        account.Deposit(amount);
        System.out.println("Successfully deposited " + amount + " into account number " + account.getAccountNumber());
        System.out.println("New balance: " + account.getBalance());
            
        
    }

    private static void Withdraw(){
        Customer accountHolder = getCustomer();
        System.out.println("Please enter the account number you would like to withdraw from: ");
        Account account = findAccount(accountHolder);
        if (account == null){
            System.out.println("Unable to find account with that account number, please try a different account number or customer.");
            return;
        }

        
        System.out.println("Please enter the amount you would like to withdraw: ");
        String amountString = sc.nextLine();
        double amount = Double.parseDouble(amountString);
        account.Withdraw(amount);
        System.out.println("Successfully withdrew " + amount + " from account number " + account.getAccountNumber());
        System.out.println("New balance: " + account.getBalance());
               
    }

    private static Customer getCustomer(){
        System.out.println("Please enter the username of the account holder: ");
        String username = sc.nextLine();
        Customer accountHolder = null;
        for (Customer c : customers){
            if (c.getUsername().equals(username)){
                accountHolder = c;
                break;
            }
        }
        if (accountHolder == null){
            System.out.println("Unable to find customer with that username");
            System.out.println("Let's create a new customer; UNDER CONSTRUCTION");
        }
        return accountHolder;

    }

    private static void CreateAccount() {
        Customer accountHolder = getCustomer();

        System.out.println("Would you like a (1) checking account or (2) savings account?");
        String response = sc.nextLine();
        int option = Integer.parseInt(response.trim());
        String newAccountNumber = Integer.toString(counter++);

        switch(option){
            case 1:
                
                System.out.println("Please enter the overdraft limit for this account: ");
                String odString = sc.nextLine();
                double overdraftLimit = Double.parseDouble(odString);

                Checking newChecking = new Checking(newAccountNumber, accountHolder, overdraftLimit);
                accountHolder.getAccounts().add(newChecking);
                System.out.println("Successfully created checking account with account number " + newChecking.getAccountNumber());
                break;
            case 2:

                Savings newSavings = new Savings(newAccountNumber, accountHolder);
                accountHolder.getAccounts().add(newSavings);
                System.out.println("Successfully created savings account with account number " + newSavings.getAccountNumber());
                break;
        }
    }

    public static void ViewAllAccounts(){
        Customer accountHolder = getCustomer();
        List<Account> accounts = accountHolder.getAccounts();
        if (accounts.size() == 0){
            System.out.println("This customer has no accounts");
            return;
        }
        for (Account a : accounts){
            System.out.println("\n\tAccount Number: " + a.getAccountNumber());
            System.out.println("\tAccount Type: " + (a instanceof Checking ? "Checking" : "Savings"));
            System.out.println("\tBalance: " + a.getBalance());
            System.out.println("\n");
        }
    }

    private static void LaunchCustomerMenu(){
        do {
            System.out.println("\n\n____Customer Menu____");

            String response = sc.nextLine();
        }
        while (true);
    }

    private static void PopulateCustomers(){
        Customer c1 = new Customer(counter++, "John", "Doe", new ArrayList<>(), "JohnDoe", "password".hashCode());
        Customer c2 = new Customer(counter++, "Jane", "Smith", new ArrayList<>(), "JaneSmith", "1243".hashCode());

        customers.add(c1);
        customers.add(c2);
    }

    private static void AddAdmin(){
        Admin admin = new Admin();
        users.add(admin);
    }
    
    
    private static void PrintWelcomeMessage(){
        System.out.println("Welcome to the Zork Bank, a new way to keep your money safe.");
    }

    private static User checkValidUser(){
        System.out.println("Please enter your userid and password separated by a space: ");
        String returnString = sc.nextLine();
        String[] userpass = returnString.split(" ");
        //DEBUG
        //System.out.println("Username: " + userpass[0]);
        //System.out.println("Password: " + userpass[1]);

        String username = userpass[0].trim();
        String password = userpass[1].trim();
        int passhash = password.hashCode();

        int userIndex = -1;
        for (int i = 0; i < users.size(); i++){
            if (users.get(i).getUsername().equals(username)){
                userIndex = i;
                break;
            }
        }

        if (userIndex == -1){
            System.out.println("System does not recognize that user");
            return null;
        }

        if (users.get(userIndex).getPasswordHash() != passhash){
            System.out.println("Password is incorrect");
            return null;
        }

        return users.get(userIndex);
    }

    


}

class Customer extends User{
    private int id;
    private String firstName;
    private String lastName;
    private List<Account> accounts;


    public Customer(int id, String firstName, String lastName, List<Account> accounts, String username, int passwordHash){
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.accounts = accounts;
        super(username,passwordHash);
    }

    public int getId() {return id;}
    public String getFirstName() {return firstName;}
    public String getLastName() {return lastName;}

    public List<Account> getAccounts() { return accounts;}

    public void setId(int id){this.id = id;}
    public void setFirstName(String name){this.firstName = name;}
    public void setLastName(String name){this.lastName = name;}

}

class Admin extends User{
    
    public Admin(int id, String username, int passwordHash){
        super(username,passwordHash);
    }
    public Admin() {
        String defaultUsername = "Admin";
        int defaultPasswordHash = "admin123".hashCode();
    
        super(defaultUsername, defaultPasswordHash);
    }
}

class User{
    private String username;
    private int passwordHash;

    public User(String username, int passwordHash){
        this.username = username;
        this.passwordHash = passwordHash;
    }

    public String getUsername(){return username;}
    public int getPasswordHash(){return passwordHash;}

    public void setUsername(String username){this.username = username;}
    public void setPasswordHash(int passHash){this.passwordHash = passHash;}

    

}

class Savings extends Account {
    String accountType = "Savings Account";
    Double interestRate = 0.05;
    public Savings(String accountNumber, Customer accountHolder){
        super(accountNumber, accountHolder);
    }
    public double addInterest(){
        setBalance(getBalance() * 1.05);
        return getBalance();
    }
    public void Withdraw(double amount){
        if (amount > getBalance()){
            System.out.println("Insufficient funds");
            return;
        }
        if (getBalance()-amount < 100){
            System.out.println("Unable to bring funds beneath $100");
            return;
        }

        setBalance(getBalance() - amount);
    }
    public void PrintReceipt(){
        System.out.println("Current balance: " + getBalance());
    }
}

class Checking extends Account {
    String accountType = "Checking Account";
    double overdraftLimit;
    public Checking(String accountNumber, Customer accountHolder, double overdraftLimit){
        super(accountNumber, accountHolder);
        this.overdraftLimit = overdraftLimit;
    }
    public double addInterest(){
        setBalance(getBalance() * 1.03);
        return getBalance();
    }
    public void Withdraw(double amount){
        if (amount > getBalance()){
            System.out.println("Insufficient funds");
            return;
        }

        if (getBalance()-amount < -overdraftLimit){
            System.out.println("Unable to bring funds beneath $100");
            return;
        }

    
        setBalance(getBalance() - amount);
    }
    public void PrintReceipt(){
        System.out.println("Current balance: " + getBalance());
    }
}

abstract class Account implements ITransaction{
    private String accountNumber;
    private Customer accountHolder;
    private double balance;
    public Account(String accountNumber, Customer accountHolder){
        this.accountNumber = accountNumber;
        this.accountHolder = accountHolder;
        this.balance = 0.0;
    }

    public String getAccountNumber() { return accountNumber; }
    public void setAccountNumber(String accountNumber) { this.accountNumber = accountNumber; }

    public Customer getAccountHolder() { return accountHolder; }
    public void setAccountHolder(Customer accountHolder) { this.accountHolder = accountHolder; }

    public double getBalance() { return balance; }
    public void setBalance(double balance) { this.balance = balance; }

    void Deposit(double amount){
        setBalance(getBalance() + amount);
    }
    abstract void Withdraw(double amount);


}

interface ITransaction{
    void PrintReceipt();
}


