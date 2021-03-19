package dev.bennett.services;

import com.auth0.jwt.interfaces.DecodedJWT;
import dev.bennett.controllers.Controller;
import dev.bennett.daos.ExpenseDAO;
import dev.bennett.daos.ExpenseDaoHibernate;
import dev.bennett.entities.ApprovalStatus;
import dev.bennett.entities.Expense;
import dev.bennett.utils.JwtUtil;
import org.apache.log4j.Logger;

import java.util.Set;

public class ExpenseServiceImp implements ExpenseService{

    private ExpenseDAO expenseDAO = new ExpenseDaoHibernate();
    private static Logger logger = Logger.getLogger(Controller.class.getName());

    @Override
    public Expense createExpenseReport(Expense expenseReport) {
        return expenseDAO.createExpense(expenseReport);
    }

    @Override
    public Set<Expense> getAllEmployeesExpenses(int employeeID) {
        return expenseDAO.getAllEmployeesExpenses(employeeID);
    }

    @Override
    public Set<Expense> getAllExpenses() {
        return expenseDAO.getAllExpenses();
    }

    @Override
    public Expense updateReimbursements(int expenseID, ApprovalStatus approval, String reason, String jwt) {
        DecodedJWT decode = JwtUtil.isValidJWT(jwt);

        try {
            logger.debug(decode.getClaim("title").asString());
            if (decode.getClaim("title").asString().equals("MANAGER")) {
                logger.debug("Manager found");
                Expense expense = expenseDAO.getExpenseByID(expenseID);
                expense.setStatus(approval);
                expense.setReasonApprovedOrDenied(reason);
                expense.setDateApprovedDenied(System.currentTimeMillis());
                expenseDAO.updateExpense(expense);
                return expense;
            }
        }
        catch (NullPointerException e){
            e.printStackTrace();
        }
        return null;
    }
}
