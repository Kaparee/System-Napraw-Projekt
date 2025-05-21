package pl.naprawy.service;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import pl.naprawy.model.Client;
import pl.naprawy.model.RepairOrder;
import pl.naprawy.util.AlertUtil;
import pl.naprawy.util.HibernateUtil;

import java.util.List;

public class ClientService implements IClientService{
    public Client getClientByLogin(String login) {
        try(Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "SELECT c FROM Client c JOIN UserAccount u ON c.id = u.client.id WHERE u.login = :login";
            Query<Client> query = session.createQuery(hql, Client.class);
            query.setParameter("login", login);
            return query.uniqueResult();
        } catch (HibernateException e) {
            AlertUtil.errorAlert("Wystąpił błąd podczas pobierania danych klienta.");
            return null;
        }
    }

    public List<Client> getAllClientInCompanies(Long id){
        try(Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "SELECT DISTINCT c FROM Client c JOIN RepairOrder ro ON ro.client.id = c.id WHERE ro.company.id IN (SELECT tc.company.id FROM TechnicianCompany tc WHERE tc.technician.id = :id) ORDER BY c.company.id";
            Query<Client> query = session.createQuery(hql, Client.class);
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
            Client client = session.get(Client.class, id);
            if (client != null){
                session.delete(client);
            }
            transaction.commit();
            session.close();
        }catch (HibernateException e) {
            e.printStackTrace();
            AlertUtil.errorAlert("Wystąpił błąd podczas usuwania pracownika.");
        }
    }
}
