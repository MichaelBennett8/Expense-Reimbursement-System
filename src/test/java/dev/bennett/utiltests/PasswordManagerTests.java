package dev.bennett.utiltests;

import com.sun.source.tree.AssertTree;
import dev.bennett.utils.PasswordManager;
import dev.bennett.utils.PasswordNoEncryption;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PasswordManagerTests {

    private static PasswordManager pm = new PasswordNoEncryption();
    /*
    String encryptPassword(String password);
    boolean checkPassword(String encryptedPassword, String checkThisPassword);
     */

    @Test
    void encryptPasswordTest(){
        String password = "suppergoodpassword";
        String encrypted = pm.encryptPassword(password);

        Assertions.assertFalse(password.equals(encrypted));
    }

    @Test
    void checkPasswordTest(){
        String password = "suppergoodpassword";
        String encrypted = pm.encryptPassword(password);

        Assertions.assertTrue(pm.checkPassword(encrypted, password));
    }
}
