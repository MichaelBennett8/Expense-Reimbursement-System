package dev.bennett.serviceTests;

import dev.bennett.daos.EmployeeDAO;
import dev.bennett.daos.EmployeeDaoHibernate;
import dev.bennett.entities.Employee;
import dev.bennett.entities.JobTitle;
import dev.bennett.services.EmployeeService;
import dev.bennett.services.loginService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class EmployeeServiceTests {

    private static EmployeeService employeeService = new loginService();
    private EmployeeDAO employeeDAO = new EmployeeDaoHibernate();

    @Test
    void loginTest(){
        Employee employee = employeeDAO.getEmployeeByID(2);

        String jwt = employeeService.login(employee, "12345");

        System.out.println(jwt);

        Assertions.assertNotNull(jwt);
        Assertions.assertEquals("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJlbXBsb3llZU5hbWUiOiJNaWtlIiwidGl0bGUiOiJFTVBMT1lFRSJ9.7zLfyforwrt04WGhP6qUbLEVjnTON3qmp44XZp7erQw", jwt);
    }

    @Test
    void getEmployeeByNameTest(){
        Employee employee = employeeService.getEmployeeByName("Maverick");

        Assertions.assertNotNull(employee);
        Assertions.assertEquals(JobTitle.EMPLOYEE, employee.getJobTitle());
    }
}
