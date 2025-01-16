package main.project.service;

import main.project.model.Account;
import main.project.model.Ewallet;

public class AccountServiceImpl implements AccountService {

    Ewallet ewallet = Ewallet.getInstance();

    @Override
    public boolean createAccount(Account account) {
        // TODO get List of Account on Ewallet and make sure that  any account with same user name
        // TODO if not exist any account not has same username add account and return true
        // TODO else return false

        for (Account existAccount : ewallet.getAccounts()) {
            if ((existAccount.getUserName().equals(account.getUserName()))) {
                return false;
            }
        }
        ewallet.addAccount(account);
        return true;

    }

    @Override
    public boolean loginAccount(Account account) {
        // TODO get List of Account on Ewallet and make sure that exist account with same user name and password
        // TODO if exist any account  has same username and password return true
        // TODO else return false
        for (Account existingAccount : ewallet.getAccounts()) {
            if (existingAccount.getUserName().equals(account.getUserName()) &&
                    existingAccount.getPassword().equals(account.getPassword())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean deposit(double amount) {
        for (Account existingAccount : ewallet.getAccounts()) {
            if (amount >= 100 && amount <= 8000 && existingAccount.getActive()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean withdraw(double amount) {
        for (Account existingAccount : ewallet.getAccounts()) {
            if (existingAccount.getBalance() >= amount && amount >=100 && amount <=8000 && existingAccount.getActive()) {
                return true;
            }
        }
        return false;
    }

    public boolean transfer(double amount, Account from, Account to) {
        boolean from1= false;
        boolean to2= false;
        for (Account existingAccount : ewallet.getAccounts()) {
            if (existingAccount.getUserName().equals(from.getUserName()) && existingAccount.getBalance() >= amount && existingAccount.getActive()) {
                from1 = true;
                break;
            }

        }
        for (Account existingAccount : ewallet.getAccounts()) {
            if (existingAccount.getUserName().equals(to.getUserName()) && existingAccount.getActive()) {
                to2 = true;
                break;
            }
        }
        if(from1 && to2) {return true;}
        return false;
    }
}