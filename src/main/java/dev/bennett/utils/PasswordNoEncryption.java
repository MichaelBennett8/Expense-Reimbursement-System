package dev.bennett.utils;

public class PasswordNoEncryption implements PasswordManager{


    @Override
    public String encryptPassword(String password) {
        return password;
    }

    @Override
    public boolean checkPassword(String encryptedPassword, String checkThisPassword) {
        return encryptedPassword.equals(checkThisPassword);
    }
}
