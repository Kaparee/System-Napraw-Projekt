package pl.naprawy.service;

import org.hibernate.Session;
import org.hibernate.query.Query;
import pl.naprawy.model.Client;
import pl.naprawy.util.HibernateUtil;

public class ClientService implements IClientService{
    Session session = HibernateUtil.getSessionFactory().openSession();


    public Client getClientByLogin(String login) {
        String hql = "SELECT c FROM Client c JOIN UserAccount u ON c.id = u.client.id WHERE u.login = :login";
        Query<Client> query = session.createQuery(hql, Client.class);
        query.setParameter("login",login);

        Client result = query.uniqueResult();
        return result;
    }
}
