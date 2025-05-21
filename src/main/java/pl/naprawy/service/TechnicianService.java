package pl.naprawy.service;

import at.favre.lib.crypto.bcrypt.BCrypt;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;
import pl.naprawy.model.RepairOrder;
import pl.naprawy.model.Technician;
import pl.naprawy.model.UserAccount;
import pl.naprawy.util.AlertUtil;
import pl.naprawy.util.HibernateUtil;
import java.util.List;

import java.sql.Timestamp;

public class TechnicianService implements ITechnicianService{
    public Technician getTechnicianByLogin(String login) {
        try(Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "SELECT t FROM Technician t JOIN UserAccount u ON t.id = u.technician.id WHERE u.login = :login";
            Query<Technician> query = session.createQuery(hql, Technician.class);
            query.setParameter("login", login);
            return query.uniqueResult();
        } catch (HibernateException e) {
            AlertUtil.errorAlert("Wystąpił błąd podczas pobierania danych technika");
            return null;
        }
    }

    public void claimRaport(Long technician_id, Long id, Timestamp now){
        try(Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            String hql = "UPDATE RepairOrder ro SET ro.technician.id = :technician_id, ro.status = 'W trakcie', ro.updated_at= :now WHERE ro.id = :id";
            Query query = session.createQuery(hql);
            query.setParameter("technician_id", technician_id);
            query.setParameter("id", id);
            query.setParameter("now", now);
            int rowsUpdated = query.executeUpdate();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            AlertUtil.errorAlert("Wystąpił błąd podczas przypisywania zgłoszenia");
        }
    }

    public boolean isPasswordCorrect(Long id, String password){
        try(Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "SELECT ua FROM UserAccount ua WHERE ua.technician.id = :id";
            Query<UserAccount> query = session.createQuery(hql, UserAccount.class);
            query.setParameter("id", id);
            UserAccount user = query.uniqueResult();

            if (user == null){
                return false;
            }
            String hashedPassword = user.getSecured_password();
            BCrypt.Result result = BCrypt.verifyer().verify(password.toCharArray(),hashedPassword);
            return result.verified;
        } catch (HibernateException e) {
            AlertUtil.errorAlert("Wystąpił błąd podczas przypisywania zgłoszenia");
            return false;
        }
    }
}