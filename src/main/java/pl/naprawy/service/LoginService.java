package pl.naprawy.service;

import org.hibernate.query.Query;
import org.hibernate.Session;
import pl.naprawy.util.HibernateUtil;

public class LoginService {
    public int verifyLogin(String login, String password){
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            String hql = "SELECT client_id, technician_id FROM UserAccount WHERE login = :login AND secured_password = :password";
            Query<Object[]> query = session.createQuery(hql, Object[].class);
            query.setParameter("login",login);
            query.setParameter("password",password);

            Object[] result = query.uniqueResult();
            if (result!=null){
                Integer clientID = (Integer) result[0];
                Integer technicianID = (Integer) result[1];

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
