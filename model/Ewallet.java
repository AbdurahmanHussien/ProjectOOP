package main.project.model;

import java.util.ArrayList;
import java.util.List;

public class Ewallet {

    private Ewallet() {}
    private static Ewallet ewallet = null;
    public static Ewallet getInstance() {
        if (ewallet == null) {
            ewallet = new Ewallet();
        }
        return ewallet;
    }


    private String name = "EraaSoft Cash";

    public String getName() {
        return name;
    }

    private List<Account> accounts = new ArrayList<>();

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }

    public void addAccount(Account account) {
        accounts.add(account);
    }


}
