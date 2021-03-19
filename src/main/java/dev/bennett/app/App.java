package dev.bennett.app;

import dev.bennett.controllers.Controller;
import io.javalin.Javalin;

import org.apache.log4j.Logger;

public class App {

    private static Logger logger = Logger.getLogger(App.class.getName());

    public static void main(String[] args){

        Javalin app = Javalin.create(config -> {
            config.enableCorsForAllOrigins();
        });

        //Javalin app = Javalin.create();

        Controller control = new Controller();

        logger.debug("controller constructed");



        //Post /login
        app.post("/login", control.loginHandler);

        //Post /expense - create expense
        app.post("/expense", control.createExpensesHandler);

        //get /expense - get all expenses
        //get /expense/5 - get expense of employee 5
        app.get("/expense", control.getExpensesHandler);
        app.get("/expense/:employeeId", control.getExpensesHandler);

        //put /expense/:expenseID/:approval - update expense
        //app.put("/expense/:expenseID/:approval", control.updateExpensesHandler);
        app.put("/expense/:expenseID", control.updateExpensesHandlerFromBody);

        logger.debug("Handerlers bound");
        logger.debug("Calling app.start()");
        app.start();
    }
}
