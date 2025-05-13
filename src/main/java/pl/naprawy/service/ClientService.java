package pl.naprawy.service;

import org.hibernate.Session;
import org.hibernate.query.Query;
import pl.naprawy.model.Client;
import pl.naprawy.util.HibernateUtil;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

public class ClientService {
    Session session = HibernateUtil.getSessionFactory().openSession();


    public Client getClientByLogin(String login) {
        String hql = "SELECT c FROM Client c JOIN UserAccount u ON c.id = u.client_id WHERE u.login = :login";
        Query<Client> query = session.createQuery(hql, Client.class);
        query.setParameter("login",login);

        Client result = query.uniqueResult();
        return result;
    }
}
