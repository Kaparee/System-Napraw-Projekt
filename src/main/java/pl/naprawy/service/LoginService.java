package pl.naprawy.service;

import org.hibernate.query.Query;
import org.hibernate.Session;
import pl.naprawy.util.HibernateUtil;

public class LoginService implements ILoginService {
    public int verifyLogin(String login, String password){
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            String hql = "SELECT ua.client.id, ua.technician.id FROM UserAccount ua WHERE ua.login = :login AND ua.secured_password = :password";
            Query<Object[]> query = session.createQuery(hql, Object[].class);
            query.setParameter("login",login);
            query.setParameter("password",password);

            Object[] result = query.uniqueResult();
            if (result!=null){
                Long clientID = (Long) result[0]; // IDs are usually Long
                Long technicianID = (Long) result[1];

                if (clientID!=null){
                    return 1;
                } else if (technicianID!=null) {
                    return 2;
                }
            }
            return 0;
        } finally {
            session.close();
        }
    }
}
