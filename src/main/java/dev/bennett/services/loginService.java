package dev.bennett.services;

import dev.bennett.daos.*;
import dev.bennett.entities.Employee;
import dev.bennett.utils.JwtUtil;
import dev.bennett.utils.PasswordManager;
import dev.bennett.utils.PasswordNoEncryption;

public class loginService implements EmployeeService{

    private PasswordDAO pwDAO = new PasswordDaoPostgresql();
    private PasswordManager pwm = new PasswordNoEncryption();
    private EmployeeDAO employeeDAO = new EmployeeDaoHibernate();

    @Override
    public String login(Employee employee, String password) {
        String expectedPassword = pwDAO.getPasswordByID(employee.getEmployeeID());

        if (pwm.checkPassword(expectedPassword, password)){
            return JwtUtil.generate(employee.getJobTitle().toString(), employee.getEmployeeName());
        }
        return null;
    }

    @Override
    public Employee getEmployeeByName(String name) {
        return employeeDAO.getEmployeeByName(name);
    }
}
