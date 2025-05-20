package pl.naprawy.service;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;
import pl.naprawy.model.Technician;
import pl.naprawy.util.AlertUtil;
import pl.naprawy.util.HibernateUtil;

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

}