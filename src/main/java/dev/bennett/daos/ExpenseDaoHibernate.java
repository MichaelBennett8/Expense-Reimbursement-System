package dev.bennett.daos;

import dev.bennett.entities.Employee;
import dev.bennett.entities.Expense;
import dev.bennett.utils.HibernateUtil;
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

public class ExpenseDaoHibernate implements ExpenseDAO{

    private SessionFactory sf = HibernateUtil.getSessionFactory();

    @Override
    public Expense createExpense(Expense expense) {
        Session sess = sf.openSession();
        sess.getTransaction().begin();
        sess.save(expense);
        sess.getTransaction().commit();
        sess.close();
        return expense;
    }

    @Override
    public Set<Expense> getAllEmployeesExpenses(int employeeID) {
        Session sess = sf.openSession();

        CriteriaBuilder criteriaBuilder = sess.getCriteriaBuilder();
        CriteriaQuery<Expense> query = criteriaBuilder.createQuery(Expense.class);
        Root<Expense> root = query.from(Expense.class);
        query.select(root).where(criteriaBuilder.equal(root.get("employeeID"), employeeID));


        Query<Expense> q = sess.createQuery(query);

        System.out.println(q.getQueryString());

        List<Expense> results = q.getResultList();

        return new HashSet<Expense>(results);
    }

    @Override
    public Expense getExpenseByID(int expenseID) {
        Session sess = sf.openSession();
        Expense expense = sess.get(Expense.class, expenseID);
        sess.close();
        return expense;
    }

    @Override
    public Expense updateExpense(Expense expense) {
        Session sess = sf.openSession();
        sess.getTransaction().begin();
        sess.update(expense);
        sess.getTransaction().commit();
        sess.close();
        return expense;
    }

    @Override
    public boolean deleteExpense(int expenseID) {
        try{
            Session sess = sf.openSession();
            sess.getTransaction().begin();
            sess.delete(this.getExpenseByID(expenseID));
            sess.getTransaction().commit();
            sess.close();
            return true;
        }catch (HibernateException he){ // in hibernate all exceptions are a runtime HobernateException
            he.printStackTrace();
            return false;
        }
    }

    @Override
    public Set<Expense> getAllExpenses() {
        Session sess = sf.openSession();

        CriteriaBuilder criteriaBuilder = sess.getCriteriaBuilder();
        CriteriaQuery<Expense> query = criteriaBuilder.createQuery(Expense.class);
        Root<Expense> root = query.from(Expense.class);
        query.select(root);

        Query<Expense> q = sess.createQuery(query);
        List<Expense> results = q.getResultList();

        return new HashSet<Expense>(results);
    }
}
