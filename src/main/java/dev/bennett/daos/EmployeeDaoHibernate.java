package dev.bennett.daos;

import dev.bennett.controllers.Controller;
import dev.bennett.entities.Employee;
import dev.bennett.entities.Expense;
import dev.bennett.utils.HibernateUtil;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class EmployeeDaoHibernate implements EmployeeDAO{

    private SessionFactory sf = HibernateUtil.getSessionFactory();
    private static Logger logger = Logger.getLogger(Controller.class.getName());

    @Override
    public Employee createEmployee(Employee employee) {
        logger.debug("createEmployee called");
        Session sess = sf.openSession();
        sess.getTransaction().begin();
        sess.save(employee); // it changes the object to have the new id
        sess.getTransaction().commit();
        sess.close();// you chould close your session
        return employee;
    }

    @Override
    public Set<Employee> getAllEmployees() {
        logger.debug("getAllEmployees called");
        Session sess = sf.openSession();

        CriteriaBuilder criteriaBuilder = sess.getCriteriaBuilder();
        CriteriaQuery<Employee> query = criteriaBuilder.createQuery(Employee.class);
        Root<Employee> root = query.from(Employee.class);
        query.select(root);

        Query<Employee> q = sess.createQuery(query);
        List<Employee> results = q.getResultList();

        return new HashSet<Employee>(results);
    }

    @Override
    public Employee getEmployeeByID(int employeeID) {
        logger.debug("getEmployeeByID called");
        Session sess = sf.openSession();
        Employee employee = sess.get(Employee.class, employeeID);
        sess.close();
        return employee;
    }

    @Override
    public Employee updateEmployee(Employee employee) {
        logger.debug("updateEmployee called");
        Session sess = sf.openSession();
        sess.getTransaction().begin();
        sess.update(employee);
        sess.getTransaction().commit();
        sess.close();
        return employee;
    }

    @Override
    public boolean deleteEmployee(int employeeID) {
        logger.debug("deleteEmployee called");
        try{
            Session sess = sf.openSession();
            sess.getTransaction().begin();
            sess.delete(this.getEmployeeByID(employeeID));
            sess.getTransaction().commit();
            sess.close();
            return true;
        }catch (HibernateException he){ // in hibernate all exceptions are a runtime HobernateException
            he.printStackTrace();
            return false;
        }
    }

    @Override
    public Employee getEmployeeByName(String name) {
        logger.debug("getEmployeeByName called with name: " + name);
        Session sess = sf.openSession();

        CriteriaBuilder criteriaBuilder = sess.getCriteriaBuilder();
        CriteriaQuery<Employee> query = criteriaBuilder.createQuery(Employee.class);
        Root<Employee> root = query.from(Employee.class);
        query.select(root).where(criteriaBuilder.equal(root.get("employeeName"), name));

        Query<Employee> q = sess.createQuery(query);

        System.out.println(q.getQueryString());

        List<Employee> results = q.getResultList();

        if (results.size() == 0){
            return null;
        }

        return results.get(0);
    }
}
