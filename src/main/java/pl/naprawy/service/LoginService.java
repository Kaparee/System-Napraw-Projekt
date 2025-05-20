package pl.naprawy.service;

import at.favre.lib.crypto.bcrypt.BCrypt;
import org.hibernate.HibernateException;
import org.hibernate.query.Query;
import org.hibernate.Session;
import pl.naprawy.model.UserAccount;
import pl.naprawy.util.AlertUtil;
import pl.naprawy.util.HibernateUtil;

public class LoginService implements ILoginService {
    public int verifyLogin(String login, String password){
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "SELECT ua FROM UserAccount ua WHERE ua.login = :login";
            Query<UserAccount> query = session.createQuery(hql, UserAccount.class);
            query.setParameter("login", login);

            UserAccount user = query.uniqueResult();
            if (user == null) {
                return 0;
            }

            String hashedPassword = user.getSecured_password();
            BCrypt.Result result = BCrypt.verifyer().verify(password.toCharArray(), hashedPassword);

            if (result.verified) {
                if (user.getClient() != null) {
                    return 1;
                } else if (user.getTechnician() != null) {
                    return 2;
                }
            }
            return 0;
        } catch (HibernateException e){
            AlertUtil.errorAlert("Wystąpił błąd podczas logowania");
            return 0;
        }
    }
}
