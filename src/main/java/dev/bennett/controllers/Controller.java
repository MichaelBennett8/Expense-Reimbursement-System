package dev.bennett.controllers;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.google.gson.Gson;
import dev.bennett.daos.PasswordDAO;
import dev.bennett.daos.PasswordDaoPostgresql;
import dev.bennett.entities.*;
import dev.bennett.services.EmployeeService;
import dev.bennett.services.ExpenseService;
import dev.bennett.services.ExpenseServiceImp;
import dev.bennett.services.loginService;

import dev.bennett.utils.JwtUtil;
import dev.bennett.utils.PasswordManager;
import dev.bennett.utils.PasswordNoEncryption;
import io.javalin.http.Handler;
import org.apache.log4j.Logger;

import java.util.Set;

public class Controller {

    private static Logger logger = Logger.getLogger(Controller.class.getName());

    private EmployeeService loginService = new loginService();
    private ExpenseService expenseService = new ExpenseServiceImp();
    private PasswordDAO passwordDAO = new PasswordDaoPostgresql();
    private PasswordManager pm = new PasswordNoEncryption();
    private JwtUtil jwtUtil = new JwtUtil();

    private Gson gson = new Gson();

    //Client Handlers

    //Post /login
    public Handler loginHandler = (ctx) -> {
        logger.debug("loginHandler called");

        Authenticator authenticator = gson.fromJson(ctx.body(), Authenticator.class);
        String name = authenticator.getName();
        String password = authenticator.getPassword();

        logger.debug("recieved name and password: " + name + " " + password);

        try {

            Employee employee = loginService.getEmployeeByName(name);
            logger.debug("employee:" + employee);
            String expextedPassword = passwordDAO.getPasswordByID(employee.getEmployeeID());

            if (pm.checkPassword(expextedPassword, password)) {
                String jwt = JwtUtil.generate(employee.getJobTitle().toString(), employee.getEmployeeName());
                ctx.result(gson.toJson(jwt));
                ctx.status(200);
            } else {
                ctx.result("Could not find username or password.");
                ctx.status(401);
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
            ctx.result("Could not find username or password.");
            ctx.status(401);
        }

    };

    //Post /expense - create expense
    public Handler createExpensesHandler = (ctx) -> {
        //TODO testing bad requests
        //String jwt = ctx.queryParam("jwt", "NONE");

//        JsonObject jsonObject = gson.fromJson(ctx.body(), JsonObject.class);
//        String jwt = jsonObject.get("jwt").getAsString();

//        myJWT jwtObject = gson.fromJson(ctx.body(), myJWT.class);
//        String jwt = jwtObject.getJwt();
//
//        DecodedJWT credentials = jwtUtil.isValidJWT(jwt);

        DecodedJWT credentials = jwtUtil.isValidJWT(ctx.header("jwt"));

        if (credentials != null) {
            String employeeName = credentials.getClaim("employeeName").asString();
            Employee employee = loginService.getEmployeeByName(employeeName);

            Expense expense = gson.fromJson(ctx.body(), Expense.class);
            expense.setEmployeeID(employee.getEmployeeID());
            expense.setStatus(ApprovalStatus.PENDING);
            expense.setDateSubmitted(System.currentTimeMillis());
            expense.setDateApprovedDenied(0);
            expense.setReasonApprovedOrDenied("");

            expenseService.createExpenseReport(expense);

            ctx.result(gson.toJson(expense));
            ctx.status(201);
        } else {
            ctx.result("could not authenticate login");
            ctx.status(401);
        }
    };

    //get /expense - get all expenses
    //get /expense/5 - get expense of employee 5
    public Handler getExpensesHandler = (ctx) -> {
        //TODO testing again after re-write
        logger.debug("getExpensesHandler called with: " + ctx.path());

        System.out.println("hedder: " + ctx.header("jwt"));

        String jwt = ctx.header("jwt");
        DecodedJWT credentials = jwtUtil.isValidJWT(jwt); //TODO make sure this doesn't break if jwt == null

        if (jwt == null || credentials == null) {//TODO is jwt == null redundant?
            logger.debug("Invalid JWT");
            ctx.result("Please login");
            ctx.status(401);
            return;
        }

        try {
            String employeeIdString = ctx.pathParam("employeeId");
            int employeeID = Integer.parseInt(employeeIdString);
            Set<Expense> expenses = expenseService.getAllEmployeesExpenses(employeeID);

            ctx.result(gson.toJson(expenses));
            ctx.status(201);
        } catch (IllegalArgumentException e) {//path param employeeId not provided
            if (credentials.getClaim("title").asString().equals("MANAGER")){
                Set<Expense> expenses = expenseService.getAllExpenses();
                ctx.result(gson.toJson(expenses));
                ctx.status(201);
            }
            else{
                String employeeName = credentials.getClaim("employeeName").asString();
                Employee employee = loginService.getEmployeeByName(employeeName);

                logger.debug("\nemployeeName: " + employeeName + " employeeObject: " + employee);

                Set<Expense> expenses = expenseService.getAllEmployeesExpenses(employee.getEmployeeID());
                ctx.result(gson.toJson(expenses));
                ctx.status(201);
            }
        }
    };

    //put /expense/:expenseID/:approval - update expense
    public Handler updateExpensesHandler = (ctx) -> {
//TODO testing bad requests
        //TODO deprecate this
        logger.debug("updateExpensesHandler called");
        int expenseID = Integer.parseInt(ctx.pathParam("expenseID"));
        int approvalOrdinal = Integer.parseInt(ctx.pathParam("approval"));
        ApprovalStatus approvalStatus = ApprovalStatus.fromInteger(approvalOrdinal);

        String jwt = ctx.header("jwt");
        DecodedJWT cridentials = jwtUtil.isValidJWT(jwt);

        ApprovalReason ar = gson.fromJson(ctx.body(), ApprovalReason.class);
        String reason = ar.getReason();
        logger.debug("\napproval reason: " + reason + "\n");
        logger.debug("ctx body " + ctx.body());

        String title = cridentials.getClaim("title").asString();
        if (cridentials != null && title.equals("MANAGER")) {
            if (this.expenseService.updateReimbursements(expenseID, approvalStatus, reason, jwt) == null) {
                //failed to update
                ctx.result("Account not found");
                ctx.status(404);
            } else {
                //updated
                logger.debug("updated expense:" + expenseID + " with status:" + approvalStatus + " for reason: " + reason);
                ctx.result("Account Updated");
                ctx.status(200);
            }
        } else {
            ctx.result("could not authenticate login");
            ctx.status(401);
        }
    };

    //put /expense/:expenseID - update expense
    public Handler updateExpensesHandlerFromBody = (ctx) -> {
        //TODO re-write the status of expense in the ctx.body instead of tha path param
        logger.debug("updateExpensesHandlerFromBody called");

        int expenseID = Integer.parseInt(ctx.pathParam("expenseID"));

        String jwt = ctx.header("jwt");
        DecodedJWT credentials = jwtUtil.isValidJWT(jwt);

        ExpenseStatusUpdate statusUpdate = gson.fromJson(ctx.body(), ExpenseStatusUpdate.class);

        //TODO check if credentials is null
        String title = credentials.getClaim("title").asString();

        if (title.equals("MANAGER")) {
            if (this.expenseService.updateReimbursements(expenseID, statusUpdate.getStatus(), statusUpdate.getReason(), jwt) == null) {
                //failed to update
                ctx.result("Account not found");
                ctx.status(404);
            } else {
                //updated
                logger.debug("updated expense:" + expenseID + " with status:" + statusUpdate.getStatus() + " for reason: " + statusUpdate.getReason());
                //ctx.result("Account Updated");
                ctx.status(200);
            }
        }
        else {
            ctx.result("could not authenticate login");
            ctx.status(401);
        }
    };
}
