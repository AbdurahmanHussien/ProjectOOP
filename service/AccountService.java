package main.project.service;

import main.project.model.Account;

public interface AccountService {

    boolean createAccount(Account account);
    boolean loginAccount(Account account);
    boolean deposit (double amount);
    boolean withdraw (double amount);
    boolean transfer (double amount, Account from, Account to);

}
