package main.project.service;

import main.project.model.Account;
import main.project.model.Ewallet;

public class ValidationServiceImpl implements ValidationService {

    @Override
    public boolean validateUserName(String userName) {

        // TODO return true if userName start with upper case and length must be greater than or equal 3
        // else false
        return userName.charAt(0) >= 65 && userName.charAt(0) <= 90 && userName.length() >= 3;
    }

    @Override
    public boolean validatePassword(String password) {
        // TODO return true if password contain number , uppercaseChar, lowerCase char and special char
        // else false
        boolean hasUppercase = false;
        boolean hasLowercase = false;
        boolean hasDigit = false;
        boolean hasSpecialChar = false;
        for (char ch : password.toCharArray()) {
            if (Character.isUpperCase(ch)) {
                hasUppercase = true;
            } else if (Character.isLowerCase(ch)) {
                hasLowercase = true;
            } else if (Character.isDigit(ch)) {
                hasDigit = true;
            } else if (!Character.isLetterOrDigit(ch)) {
                hasSpecialChar = true;
            }
        }
        return hasUppercase && hasLowercase && hasDigit && hasSpecialChar;
    }



}
