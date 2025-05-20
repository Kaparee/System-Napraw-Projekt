package pl.naprawy.service;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;
import pl.naprawy.model.Client;
import pl.naprawy.util.AlertUtil;
import pl.naprawy.util.HibernateUtil;

public class ClientService implements IClientService{
    public Client getClientByLogin(String login) {
        try(Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "SELECT c FROM Client c JOIN UserAccount u ON c.id = u.client.id WHERE u.login = :login";
            Query<Client> query = session.createQuery(hql, Client.class);
            query.setParameter("login", login);
            return query.uniqueResult();
        } catch (HibernateException e) {
            AlertUtil.errorAlert("Wystąpił błąd podczas pobierania danych klienta");
            return null;
        }
    }
}
