package dev.bennett.services;

import dev.bennett.entities.ApprovalStatus;
import dev.bennett.entities.Expense;

import java.util.Set;

public interface ExpenseService {

    /*
        Employees should be able to login.(employee)
        Employees can submit expenses.(expense)
        Every every expense should have an amount and a reason, status, date submitted, and date approved/denied.(expense)
        valid expense statuses are "pending", "approved", "denied"(expense)
        Employees can see the status of any of their own reimbursements past and present.(expense)

        Managers should be able to login(employee)
        Managers can view all employees reimbursements(expense)
        Managers can approve or deny reimbursements.(expense)
        When approving or denying managers can optionally add a reason why(expense)

        you do not need to be able to create new employees or managers. You can assume another application does that.(employee)
        You can add employees/managers direclty to the database
     */

    Expense createExpenseReport(Expense expenseReport);

    Set<Expense> getAllEmployeesExpenses(int employeeID);

    Set<Expense> getAllExpenses();

    Expense updateReimbursements(int expenseID , ApprovalStatus approval, String reason, String jwt);
}
