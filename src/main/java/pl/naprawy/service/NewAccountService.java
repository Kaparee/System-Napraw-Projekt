package pl.naprawy.service;

import at.favre.lib.crypto.bcrypt.BCrypt;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;
import pl.naprawy.model.Client;
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

    public void createNewClient(Client client, Long company_id){
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            Company company = session.get(Company.class, company_id);
            client.setCompany(company);
            session.save(client);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            AlertUtil.errorAlert("Wystąpił błąd podczas tworzenia użytkownika");
        }
    }

    public void createNewAccount(Client client, String username, String password){
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            UserAccount userAccount = new UserAccount();
            userAccount.setSecured_password(BCrypt.withDefaults().hashToString(12, password.toCharArray()));
            userAccount.setClient(client);
            userAccount.setLogin(username);
            userAccount.setTechnician(null);
            userAccount.setRole("CLIENT");
            session.save(userAccount);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            AlertUtil.errorAlert("Wystąpił błąd podczas tworzenia konta");
        }
    }
}
