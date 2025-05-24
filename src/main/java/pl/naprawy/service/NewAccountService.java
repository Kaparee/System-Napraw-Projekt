package pl.naprawy.service;

import at.favre.lib.crypto.bcrypt.BCrypt;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;
import pl.naprawy.model.Employee;
import pl.naprawy.model.Company;
import pl.naprawy.model.UserAccount;
import pl.naprawy.util.AlertUtil;
import pl.naprawy.util.HibernateUtil;

import java.util.List;

public class NewAccountService implements INewAccountService{
    public List<Company> getAllCompany(){
        try(Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "SELECT c FROM Company c";
            Query<Company> query = session.createQuery(hql, Company.class);
            return query.list();
        } catch (HibernateException e) {
            AlertUtil.errorAlert("Wystąpił błąd podczas pobierania danych firm");
            return null;
        }
    }

    public void createNewEmployee(Employee employee, Long company_id){
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            Company company = session.get(Company.class, company_id);
            employee.setCompany(company);
            session.save(employee);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            AlertUtil.errorAlert("Wystąpił błąd podczas tworzenia użytkownika");
        }
    }

    public void createNewAccount(Employee employee, String username, String password){
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            UserAccount userAccount = new UserAccount();
            userAccount.setSecured_password(BCrypt.withDefaults().hashToString(12, password.toCharArray()));
            userAccount.setEmployee(employee);
            userAccount.setLogin(username);
            userAccount.setTechnician(null);
            userAccount.setRole("EMPLOYEE");
            session.save(userAccount);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            AlertUtil.errorAlert("Wystąpił błąd podczas tworzenia konta");
        }
    }

    public boolean isUsernameTaken(String username) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "SELECT COUNT(ua) FROM UserAccount ua WHERE ua.login = :username";
            Query<Long> query = session.createQuery(hql, Long.class);
            query.setParameter("username", username);
            return query.uniqueResult() > 0;
        } catch (HibernateException e) {
            e.printStackTrace();
            AlertUtil.errorAlert("Błąd bazy danych podczas sprawdzania nazwy użytkownika.");
            return true;
        }
    }

    public boolean isEmailTaken(String email) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "SELECT COUNT(e) FROM Employee e WHERE e.email = :email";
            Query<Long> query = session.createQuery(hql, Long.class);
            query.setParameter("email", email);
            return query.uniqueResult() > 0;
        } catch (HibernateException e) {
            e.printStackTrace();
            AlertUtil.errorAlert("Błąd bazy danych podczas sprawdzania e-maila.");
            return true;
        }
    }

    public boolean isPhoneNumberTaken(String phoneNumber) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "SELECT COUNT(e) FROM Employee e WHeRE e.phone = :phoneNumber";
            Query<Long> query = session.createQuery(hql, Long.class);
            query.setParameter("phoneNumber", phoneNumber);
            return query.uniqueResult() > 0;
        } catch (HibernateException e) {
            e.printStackTrace();
            AlertUtil.errorAlert("Błąd bazy danych podczas sprawdzania numeru telefonu.");
            return true;
        }
    }
}
