package dev.bennett.daos;

import dev.bennett.entities.Expense;

import java.util.Set;

public interface ExpenseDAO {

    //Create
    Expense createExpense(Expense expense);

    //READ
    Set<Expense> getAllEmployeesExpenses(int employeeID);
    Set<Expense> getAllExpenses();
    Expense getExpenseByID(int expenseID);

    //UPDATE
    Expense updateExpense(Expense expense);

    //DELETE
    boolean deleteExpense(int expenseID);
}
