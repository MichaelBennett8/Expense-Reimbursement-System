package dev.bennett.services;

import dev.bennett.entities.ApprovalStatus;
import dev.bennett.entities.Employee;
import dev.bennett.entities.Expense;

import java.util.Set;

public interface EmployeeService {

    /*
        Employees should be able to login.
        Employees can submit expenses.
        Every every expense should have an amount and a reason, status, date submitted, and date approved/denied.
        valid expense statuses are "pending", "approved", "denied"
        Employees can see the status of any of their own reimbursements past and present.

        Managers should be able to login
        Managers can view all employees reimbursements
        Managers can approve or deny reimbursements.
        When approving or denying managers can optionally add a reason why

        you do not need to be able to create new employees or managers. You can assume another application does that.
        You can add employees/managers direclty to the database
     */

    String login(Employee employee, String password);

    //Employee createEmployee(Employee employee);

    Employee getEmployeeByName(String name);
}
