package pl.naprawy.service;

import org.hibernate.Session;
import org.hibernate.query.Query;
import pl.naprawy.model.RepairOrder;
import pl.naprawy.model.Technician;
import pl.naprawy.util.HibernateUtil;

import java.util.List;

public class TechnicianService implements ITechnicianService{
    Session session = HibernateUtil.getSessionFactory().openSession();

    public Technician getTechnicianByLogin(String login) {
        String hql = "SELECT t FROM Technician t JOIN UserAccount u ON t.id = u.technician.id WHERE u.login = :login";
        Query<Technician> query = session.createQuery(hql, Technician.class);
        query.setParameter("login",login);

        Technician result = query.uniqueResult();
        return result;
    }

}