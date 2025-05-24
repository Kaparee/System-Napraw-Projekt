package pl.naprawy.service;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import pl.naprawy.model.Employee;
import pl.naprawy.model.UserAccount;
import pl.naprawy.util.AlertUtil;
import pl.naprawy.util.HibernateUtil;

import java.util.List;

public class EmployeeService implements IEmployeeService {
    public Employee getEmployeeByLogin(String login) {
        try(Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "SELECT e FROM Employee e JOIN UserAccount u ON e.id = u.employee.id WHERE u.login = :login";
            Query<Employee> query = session.createQuery(hql, Employee.class);
            query.setParameter("login", login);
            return query.uniqueResult();
        } catch (HibernateException e) {
            AlertUtil.errorAlert("Wystąpił błąd podczas pobierania danych klienta.");
            return null;
        }
    }

    public List<Employee> getAllEmployeesInCompanies(Long id){
        try(Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "SELECT DISTINCT e FROM Employee e WHERE e.company.id IN (SELECT tc.company.id FROM TechnicianCompany tc WHERE tc.technician.id = :id) ORDER BY e.company.id";
            Query<Employee> query = session.createQuery(hql, Employee.class);
            query.setParameter("id", id);
            return query.list();
        }catch (HibernateException e) {
            AlertUtil.errorAlert("Wystąpił błąd podczas pobierania danych klientów.");
            return null;
        }
    }

    public void deleteEmployee(Long id){
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            Transaction transaction = session.beginTransaction();
            Employee employee = session.get(Employee.class, id);
            if (employee != null){
                String hql = "FROM UserAccount ua WHERE ua.employee = :employee";
                Query<UserAccount> query = session.createQuery(hql, UserAccount.class);
                query.setParameter("employee", employee);
                UserAccount userAccountToDelete = query.uniqueResult();

                if (userAccountToDelete != null) {
                    session.delete(userAccountToDelete);
                }

                session.delete(employee);
            }
            transaction.commit();
            session.close();
        }catch (HibernateException e) {
            e.printStackTrace();
            AlertUtil.errorAlert("Wystąpił błąd podczas usuwania pracownika.");
        }
    }
}
