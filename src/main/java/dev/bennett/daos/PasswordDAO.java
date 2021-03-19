package dev.bennett.daos;

import dev.bennett.entities.Expense;

import java.util.Set;

public interface PasswordDAO {

    //Create
    String createPassword(int employeeID, String password);

    //READ
    String getPasswordByID(int employeeID);

    //UPDATE
    String updatePassword(int employeeID, String password);

    //DELETE
    boolean deletePassword(int employeeID);
}
