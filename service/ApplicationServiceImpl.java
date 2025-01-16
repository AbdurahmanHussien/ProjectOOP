package main.project.service;

import main.project.model.Account;
import main.project.model.Ewallet;

import java.util.Scanner;

public class ApplicationServiceImpl implements ApplicationService {

    @Override
    public void run() {
        Scanner scanner = new Scanner(System.in);

        AccountService accountService = new AccountServiceImpl();
        boolean isAccountCreated1 = accountService.createAccount(new Account("Abdo" , "Abdo21#"));
        boolean isAccountCreated2 = accountService.createAccount(new Account("Eslam" , "Eslam21#"));
        System.out.println("Welcome To " + Ewallet.getInstance().getName());
        System.out.println("--------------------------");


        int attempts =0;
        int maxAttempts = 4;
        // TODO on case (Invalid Choose) repeat for 4 times
         while (attempts < maxAttempts) {
             System.out.println("Please Enter your choice");
             System.out.println("--------------------------");
             System.out.println("a. Login");
             System.out.println("b. Signup");
             System.out.println("c. Exit");
             System.out.println("--------------------------");

             char choose = scanner.next().charAt(0);

             switch (choose) {

                 case 'a':
                     login();
                     break;
                 case 'b':
                     signup();
                     break;
                 case 'c':
                     System.out.println("You are Welcome.");
                     flag = false;
                     return;
                 default:
                     System.out.println("Invalid Choice");
                     attempts++;
                     System.out.println("Invalid Choice. Attempts left: " + (maxAttempts - attempts));
             }
         }
        System.out.println("Too many invalid attempts. Try later.");
    }


    private void signup() {
          Ewallet ewallet =Ewallet.getInstance();
        Scanner scanner = new Scanner(System.in);

        System.out.print("Please Enter Username: ");
        String name = scanner.nextLine();

        System.out.print("Please Enter Password");
        String password = scanner.nextLine();

        ValidationService validationService = new ValidationServiceImpl();

        // TODO Validation on UserName and Password
        if (!validationService.validateUserName(name)) { //
            System.out.println("Invalid Username");
            return;
        }

        if (!validationService.validatePassword(password)) {
            System.out.println("Invalid Password");
            return;
        }
        for (Account existingAccount :ewallet.getAccounts() ) {
            if (existingAccount.getUserName().equals(name)) {
                System.out.println("Username already exists");
                return;
            }
        }

        // TODO SERVICE OF ACCOUNT TO CREATE ACCOUNT

        AccountService accountService = new AccountServiceImpl();
        Account account = new Account(name, password);
        // TODO   impl createAccount
        boolean isAccountCreated = accountService.createAccount(account);

        if (isAccountCreated) {
            System.out.println("Account Created Successfully.");
            ewallet.addAccount(account);

        } else {
            System.out.println("Failed to create account.");

        }

    }

    private void login() {
        Scanner scanner = new Scanner(System.in);

        Ewallet ewallet = Ewallet.getInstance();

        System.out.println("Please Enter Username: ");
        String name = scanner.nextLine();

        System.out.println("Please Enter Password: ");
        String password = scanner.nextLine();

        ValidationService validationService = new ValidationServiceImpl();

        // TODO Validation on UserName and Password
        if (!validationService.validateUserName(name)) {
            System.out.println("Invalid Username.");
            return;
     }

        if (!validationService.validatePassword(password)) {
            System.out.println("Invalid Password.");
            return;
       }

        Account foundAccount = null;
        for (Account existingAccount : ewallet.getAccounts()) {
            if (existingAccount.getUserName().equals(name) && existingAccount.getPassword().equals(password)) {
                foundAccount = existingAccount;
                break;
            }
        }

        // TODO SERVICE OF ACCOUNT TO LOGIN
        if (foundAccount != null) {
            System.out.println("Login Successful.");
            services(foundAccount);
        } else {
            System.out.println("Account does not exist. ");
        }
     }
    private void displayMenu(String... options) {
        System.out.println("Please choose an option:");
        System.out.println("-----------------------------------------");
        for (int i = 0; i < options.length; i++) {
            System.out.println((i + 1) + ". " + options[i]);
        }
        System.out.println("-----------------------------------------");
    }

    boolean flag = true;

    private void services(Account account) {
        Scanner scanner = new Scanner(System.in);
        while (flag) {
            displayMenu("Deposit", "Withdraw", "Show Details", "Transfer", "Show Balance", "Modify Credentials", "Sign Out");
            // TODO create switch case such as on run function

            char choose = scanner.next().charAt(0);

            switch (choose) {

                case '1':
                    deposit(account);
                    break;
                case '2':
                    withdraw(account);
                    break;
                case '3':
                    showDetails(account);
                    break;

                case '4':
                    transfer(account, account);
                    break;
                case '5':
                    showBalance(account);
                    break;

                case '6':
                    modifyCredentials(account);
                    break;
                case '7':
                    System.out.println("Signed out successfully.");
                    run();
                    break;

                default:
                    System.out.println("Invalid Choice, Try again.");

            }
        }
    }

    void deposit(Account account) {
        AccountService accountService = new AccountServiceImpl();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter deposit amount: ");
        int amount = scanner.nextInt();
        if(accountService.deposit(amount)){
            account.setBalance(account.getBalance()+amount);
        System.out.println("Deposited " + amount+ "$ Successful." +" Account Balance after depositing = " + account.getBalance() +"$" );
            System.out.println("-----------------------------------------");

        }
         else{
            System.out.println("Deposited " + amount+ "$ Failed.");
            System.out.println("-----------------------------------------");

        }
    }

    void withdraw(Account account) {
        Scanner scanner = new Scanner(System.in);
        AccountService accountService = new AccountServiceImpl();
        System.out.println("Enter withdraw amount: ");
        int amount = scanner.nextInt();
        if(accountService.withdraw(amount)) {
            account.setBalance(account.getBalance()-amount);
            System.out.println("Withdraw "+ amount +"$ Successful." +" Your Account Balance After withdrawing = " + account.getBalance()+"$" );
            System.out.println("-----------------------------------------");

        } else{
            System.out.println("you can't withdraw this amount");
            System.out.println("-----------------------------------------");

        }
    }
    void showDetails(Account account){

        System.out.println("Your Username: " +account.getUserName());
        System.out.println("Your Account password: "+ account.getPassword());
        System.out.println("Your Account balance: " +account.getBalance() +"$");
        System.out.println("Your Account Status: " + account.getActive());
        System.out.println("-----------------------------------------");

    }
    void transfer(Account account, Account receiver) {
        Scanner scanner = new Scanner(System.in);
        AccountService accountService = new AccountServiceImpl();
        Ewallet ewallet = Ewallet.getInstance();
        System.out.println("Enter the Amount to be transferred: ");
        System.out.println("-----------------------------------------");

        int amount = scanner.nextInt();

        System.out.println("Enter the username of the account to transfer to: ");
        System.out.println("-----------------------------------------");

        String targetUsername = scanner.next();
        Account targetAccount = null;
        for (Account account1 : ewallet.getAccounts()) {
            if (account1.getUserName().equals(targetUsername)) {
                receiver = account1;
                targetAccount = account1;
                break;
            }
        }
            if (targetAccount == null) {
                System.out.println("Target account does not exist. Transfer failed.");
                System.out.println("-----------------------------------------");

                return;
            }

            if (accountService.transfer(amount, account, receiver)) {
                account.setBalance(account.getBalance() - amount);
                receiver.setBalance(receiver.getBalance() + amount);
                System.out.println("Transfer Successful.");
                System.out.println("-----------------------------------------");

            } else {
                System.out.println("Transfer Failed.");
                System.out.println("-----------------------------------------");

            }
        }


    void showBalance(Account account) {
        System.out.println("Your Account Balance : " +account.getBalance() + "$");
    }
    void modifyCredentials(Account account) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter your current username:");
        String currentUsername = scanner.nextLine();
        System.out.println("Enter your current password:");
        String currentPassword = scanner.nextLine();

        if (!currentUsername.equals(account.getUserName()) || !currentPassword.equals(account.getPassword())) {
            System.out.println(" Username or password unmatched . modification not allowed.");
            return;
        }

        System.out.println("Enter your new username:");
        String newUsername = scanner.nextLine();
        System.out.println("Enter your new password:");
        String newPassword = scanner.nextLine();

        ValidationService validationService = new ValidationServiceImpl();

        if (!validationService.validateUserName(newUsername)) {
            System.out.println("Invalid new username.");
            return;
        }

        if (!validationService.validatePassword(newPassword)) {
            System.out.println("Invalid new password.");
            return;
       }
        account.setUserName(newUsername);
        account.setPassword(newPassword);
        System.out.println("Username and password updated successfully!");
    }

}
