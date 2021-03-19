package dev.bennett.daotests;

import dev.bennett.daos.EmployeeDAO;
import dev.bennett.daos.EmployeeDaoHibernate;
import dev.bennett.entities.Employee;
import dev.bennett.entities.JobTitle;
import org.junit.jupiter.api.*;
import org.testng.Assert;

import java.util.Set;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class EmployeeDaoTests {

    private static EmployeeDAO employeeDAO = new EmployeeDaoHibernate();
    private static Employee testEmployee = null;

    @Test
    @Order(1)
    void createEmployeeTest(){
        testEmployee = new Employee();
        testEmployee.setJobTitle(JobTitle.EMPLOYEE);
        testEmployee.setEmployeeName("Mike");
        employeeDAO.createEmployee(testEmployee);
        Assertions.assertNotEquals(0, testEmployee.getEmployeeID());
    }

    @Test
    @Order(2)
    void getAllEmployeesTests(){
        Set<Employee> employees = employeeDAO.getAllEmployees();
        int totalEmployees = employees.size();

        testEmployee.setEmployeeName("Nikki");

        employeeDAO.createEmployee(testEmployee);
        employees = employeeDAO.getAllEmployees();

        Assertions.assertEquals(1, employees.size() - totalEmployees);
    }

    @Test
    @Order(6)
    void getEmployeeByNameTest() {
        Employee employee = employeeDAO.getEmployeeByName("Maverick");

        Assertions.assertTrue(employee.getEmployeeName().equals("Maverick"));
        Assertions.assertNotNull(employee);
    }

    @Test
    @Order(3)
    void getEmployeeByIDTest(){
        Employee employee = employeeDAO.getEmployeeByID(testEmployee.getEmployeeID());
        Assertions.assertEquals(testEmployee.getEmployeeID(), employee.getEmployeeID());
    }

    @Test
    @Order(4)
    void updateEmployeeTest(){
        testEmployee.setEmployeeName("Goose");
        employeeDAO.updateEmployee(testEmployee);

        Employee employee = employeeDAO.getEmployeeByID(testEmployee.getEmployeeID());
        Assertions.assertEquals(testEmployee.getEmployeeName(), employee.getEmployeeName());
    }

    @Test
    @Order(5)
    void deleteEmployeeTest(){
        Assertions.assertTrue(employeeDAO.deleteEmployee(testEmployee.getEmployeeID()));

        Assertions.assertNull(employeeDAO.getEmployeeByID(testEmployee.getEmployeeID()));
    }
}
