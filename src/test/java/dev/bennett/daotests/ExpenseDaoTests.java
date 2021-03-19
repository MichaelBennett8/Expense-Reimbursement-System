package dev.bennett.daotests;

import dev.bennett.daos.ExpenseDAO;
import dev.bennett.daos.ExpenseDaoHibernate;
import dev.bennett.entities.ApprovalStatus;
import dev.bennett.entities.Employee;
import dev.bennett.entities.Expense;
import org.junit.jupiter.api.*;

import java.util.Set;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ExpenseDaoTests {

    private static ExpenseDAO expenseDAO = new ExpenseDaoHibernate();
    private static Expense testExpense = null;

    @Test
    @Order(1)
    void createExpenseTest(){
        testExpense = new Expense();
        testExpense.setEmployeeID(3);
        testExpense.setAmount(500.99);
        testExpense.setDateApprovedDenied(0);
        testExpense.setDateSubmitted(1614811150);
        testExpense.setStatus(ApprovalStatus.PENDING);

        expenseDAO.createExpense(testExpense);
        Assertions.assertNotEquals(0, testExpense.getExpenseID());
    }

    @Test
    @Order(2)
    void getAllEmployeesExpensesTests(){
        Set<Expense> expenses = expenseDAO.getAllEmployeesExpenses(3);
        int totalEmployees = expenses.size();

        testExpense = new Expense();
        testExpense.setEmployeeID(3);
        testExpense.setAmount(400.99);
        testExpense.setDateApprovedDenied(0);
        testExpense.setDateSubmitted(1614811150);
        testExpense.setStatus(ApprovalStatus.PENDING);

        expenseDAO.createExpense(testExpense);
        expenses = expenseDAO.getAllEmployeesExpenses(3);

        System.out.println(expenses.size());

        Assertions.assertEquals(1, expenses.size() - totalEmployees);
    }

    @Test
    @Order(3)
    void getExpenseByIDTest(){
        Expense expense = expenseDAO.getExpenseByID(testExpense.getExpenseID());
        Assertions.assertEquals(testExpense.getExpenseID(), expense.getExpenseID());
    }

    @Test
    @Order(4)
    void updateExpenseTest(){
        testExpense.setAmount(1337);
        expenseDAO.updateExpense(testExpense);

        Expense expense = expenseDAO.getExpenseByID(testExpense.getExpenseID());
        Assertions.assertEquals(testExpense.getAmount(), expense.getAmount());
    }

    @Test
    @Order(5)
    void deleteExpenseTest(){
        Assertions.assertTrue(expenseDAO.deleteExpense(testExpense.getExpenseID()));

        Assertions.assertNull(expenseDAO.getExpenseByID(testExpense.getExpenseID()));
    }

    @Test
    @Order(2)
    void getAllExpensesTest() {
        Set<Expense> expense = expenseDAO.getAllExpenses();
        int totalEmployees = expense.size();

        expenseDAO.createExpense(testExpense);
        expense = expenseDAO.getAllExpenses();

        Assertions.assertEquals(1, expense.size() - totalEmployees);
    }
}
