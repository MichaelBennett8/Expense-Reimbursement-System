package dev.bennett.daotests;

import dev.bennett.daos.PasswordDAO;
import dev.bennett.daos.PasswordDaoPostgresql;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PasswordDaoTests {

    private static PasswordDAO pwDAO = new PasswordDaoPostgresql();

    @Test
    void createPasswordTest() {

    }

    @Test
    void getPasswordByIDTest() {
        String password = pwDAO.getPasswordByID(2);

        Assertions.assertEquals("12345", password);
    }

    @Test
    void updatePasswordTest() {
    }

    @Test
    void deletePasswordTest() {
    }
}
