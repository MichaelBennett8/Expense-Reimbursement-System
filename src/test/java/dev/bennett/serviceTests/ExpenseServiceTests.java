package dev.bennett.serviceTests;

import dev.bennett.daos.ExpenseDAO;
import dev.bennett.entities.ApprovalStatus;
import dev.bennett.entities.Employee;
import dev.bennett.entities.Expense;
import dev.bennett.services.ExpenseService;
import dev.bennett.services.ExpenseServiceImp;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.testng.Assert;

import java.util.Set;

public class ExpenseServiceTests {

    private static ExpenseService expenseService = new ExpenseServiceImp();
    private static Employee testEmployee = null;
    private static Expense testExpence = null;

    @Test
    void createExpenseReportTest(){
        testExpence = new Expense();
        testExpence.setEmployeeID(1);

        expenseService.createExpenseReport(testExpence);

        Assertions.assertNotEquals(0, testExpence.getExpenseID());
    }

    @Test
    void getAllEmployeesExpensesTest(){
        try {
            Set<Expense> expenses = expenseService.getAllEmployeesExpenses(1);
            int expenseAmt1 = expenses.size();

            Expense expense = new Expense();
            expense.setEmployeeID(1);

            expenseService.createExpenseReport(expense);

            expenses = expenseService.getAllEmployeesExpenses(1);

            Assertions.assertEquals(1, expenses.size() - expenseAmt1);
        }
        catch (NullPointerException e){
            System.out.println("Check if employee does not exist");
            System.out.println("Check if employee has expenses");
            e.printStackTrace();
        }
    }

    @Test
    void getAllEmployeesReimbursementsTest(){

    }

    @Test
    void updateReimbursementsTest(){
        String managerJWT = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJlbXBsb3llZU5hbWUiOiJTb3BoaWUiLCJ0aXRsZSI6Ik1BTkFHRVIifQ.6fDlA9Yd4TAxMdJHuvTJgiE6jPSlZdOJ19JBzJ3M8d0";
        String employeeJWT = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJlbXBsb3llZU5hbWUiOiJNYXJ5IFN1ZSIsInRpdGxlIjoiRU1QTE9ZRUUifQ.Lft2FU6enQpuTmuhPh9nM0Km236D4gtgddnNeqYXuNY";
        String fakeJWT = "superLegitJWT";

        Expense expense = new Expense();
        expense.setEmployeeID(1);

        testExpence = expenseService.createExpenseReport(expense);

        int expenseID = testExpence.getExpenseID();

        expense = expenseService.updateReimbursements(expenseID, ApprovalStatus.APPROVED, "", managerJWT);

        Assertions.assertNotNull(expense);
        Assertions.assertEquals(ApprovalStatus.APPROVED, expense.getStatus());

        expense = expenseService.updateReimbursements(expenseID, ApprovalStatus.DENIED, "", employeeJWT);

        Assertions.assertNull(expense);

        expense = expenseService.updateReimbursements(expenseID, ApprovalStatus.DENIED, "", fakeJWT);

        Assertions.assertNull(expense);
    }
}
