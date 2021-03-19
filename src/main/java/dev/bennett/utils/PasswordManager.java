package dev.bennett.utils;

public interface PasswordManager {

    String encryptPassword(String password);

    boolean checkPassword(String encryptedPassword, String checkThisPassword);
}
