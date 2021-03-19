package dev.bennett.daos;

import dev.bennett.entities.Employee;

import java.util.Set;

public interface EmployeeDAO {

    //CREATE
    Employee createEmployee(Employee employee);

    //READ
    Set<Employee> getAllEmployees();
    Employee getEmployeeByID(int employeeID);
    Employee getEmployeeByName(String name);

    //UPDATE
    Employee updateEmployee(Employee employee);

    //DELETE
    boolean deleteEmployee(int employeeID);
}
